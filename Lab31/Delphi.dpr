Program Lab31;
Uses
  System.SysUtils;
Type
    TChar = Array Of Char;
Const
    OPTION1 = 1;
    OPTION2 = 2;
    MIN_LEN = 1;
    MAX_LEN = 100;
    FACTOR = 1.247;
Procedure PrintTask();
Begin
    WriteLn('Данная программа находит элементы в двух строках по одному из критериев.');
    WriteLn;
End;
Function CheckStringLen(Str: String) : Boolean;
Var
    IsCorrect: Boolean;
Begin
    IsCorrect := True;
    If (Length(Str) < MIN_LEN) Or (Length(Str) > MAX_LEN) Then
    Begin
        WriteLn('Длина строки не попадает в диапазон!');
        IsCorrect := False;
    End;
    CheckStringLen := IsCorrect;
End;
Function ChooseOption() : Integer;
Var
    SOption: String;
    IOption: Integer;
    IsCorrect: Boolean;
Begin
    SOption := '';
    IOption := 1;
    Repeat
        IsCorrect := True;
        ReadLn(SOption);
        Try
            IOption := StrToInt(SOption);
        Except
            WriteLn('Некорректный выбор!');
            Write('Повторите попытку: ');
            IsCorrect := False;
        End;
        If IsCorrect And (IOption <> OPTION1) And (IOption <> OPTION2) Then
        Begin
            WriteLn('Некорректный выбор!');
            Write('Повторите попытку: ');
            IsCorrect := False;
        End;
    Until IsCorrect;
    ChooseOption := IOption;
End;
Function ReadPathToFile() : String;
Var
    PathToFile: String;
    IsCorrect: Boolean;
Begin
    PathToFile := '';
    Repeat
        IsCorrect := True;
        Write('Введите путь к файлу с расширением .txt с двумя строками, с длинами[', MIN_LEN, '; ', MAX_LEN, ']: ');
        ReadLn(PathToFile);
        If ExtractFileExt(PathToFile) <> '.txt' Then
        Begin
            WriteLn('Расширение файла не .txt!');
            IsCorrect := False;
        End;
    Until IsCorrect;
    ReadPathToFile := PathToFile;
End;
Function IsNotExists(PathToFile: String) : Boolean;
Var
    IsRight: Boolean;
Begin
    IsRight := True;
    If FileExists(PathToFile) Then
        IsRight := False;
    IsNotExists := IsRight;
End;
Function IsNotAbleToReading(Var F: TextFile) : Boolean;
Var
    IsRight: Boolean;
Begin
    IsRight := False;
    Try
        Reset(F);
        CloseFile(F);
    Except
        IsRight := True;
    End;
    IsNotAbleToReading := IsRight;
End;
Function IsNotAbleToWriting(PathToFile: String) : Boolean;
Var
    IsRight: Boolean;
Begin
    IsRight := False;
    If FileIsReadOnly(PathToFile) Then
        IsRight := True;
    IsNotAbleToWriting := IsRight;
End;
Function IsEmpty(Var F: TextFile) : Boolean;
Var
    IsRight: Boolean;
Begin
    IsRight := False;
    Reset(F);
    If EOF(F) Then
        IsRight := True;
    CloseFile(F);
    IsEmpty := IsRight;
End;
Function IsNotRightCountStrings(Var F: TextFile) : Boolean;
Var
    IsRight: Boolean;
Begin
    IsRight := False;
    Reset(F);
    ReadLn(F);
    If EOF(F) Then
        IsRight := True;
    ReadLn(F);
    If Not EOF(F) Then
        IsRight := True;
    CloseFile(F);
    IsNotRightCountStrings := IsRight;
End;
Function IsNotCorrectStrings(Var F: TextFile) : Boolean;
Var
    Str: String;
    IsRight: Boolean;
Begin
    Str := '';
    Reset(F);
    ReadLn(F, Str);
    IsRight := CheckStringLen(Str);
    If IsRight Then
    Begin
        ReadLn(F, Str);
        IsRight := CheckStringLen(Str);
    End;
    CloseFile(F);
    IsNotCorrectStrings := Not IsRight;
End;
Procedure GetFileNormalReading(Var F: TextFile);
Var
    PathToFile: String;
    IsCorrect: Boolean;
Begin
    PathToFile := '';
    Repeat
        IsCorrect := True;
        PathToFile := ReadPathToFile();
        If IsNotExists(PathToFile) Then
        Begin
            IsCorrect := False;
            Writeln('Проверьте корректность ввода пути к файлу!');
        End;
        If IsCorrect Then
            AssignFile(F, PathToFile);
        If IsCorrect And IsNotAbleToReading(F) Then
        Begin
            IsCorrect := False;
            Writeln('Файл закрыт для чтения!');
        End;
        If IsCorrect And IsEmpty(F) Then
        Begin
            IsCorrect := False;
            WriteLn('Файл пуст!');
        End;
        If IsCorrect And IsNotRightCountStrings(F) Then
        Begin
            IsCorrect := False;
            Writeln('Количество строк в файле не две!');
        End;
        If IsCorrect And IsNotCorrectStrings(F) Then
            IsCorrect := False;
    Until IsCorrect;
End;
Procedure GetFileNormalWriting(Var F: TextFile);
Var
    PathToFile: String;
    IsCorrect: Boolean;
Begin
    PathToFile := '';
    Repeat
        IsCorrect := True;
        PathToFile := ReadPathToFile();
        If IsCorrect Then
            AssignFile(F, PathToFile);
        If IsNotExists(PathToFile) Then
        Begin
            IsCorrect := False;
            Writeln('Проверьте корректность ввода пути к файлу!');
        End;
        If IsCorrect And IsNotAbleToWriting(PathToFile) Then
        Begin
            IsCorrect := False;
            WriteLn('Файл закрыт для записи!');
        End;
    Until IsCorrect;
End;
Function ReadFileString(Var F: TextFile) : String;
Var
    Str: String;
Begin
    Str := '';
    ReadLn(F, Str);
    ReadFileString := Str;
End;
Function ReadConsoleString(Num: Integer) : String;
Var
    Str: String;
    IsCorrect: Boolean;
Begin
    Str := '';
    Repeat
        Write('Введите строку номер ', Num, ', с длиной[', MIN_LEN, ';', MAX_LEN, ']: ');
        Readln(Str);
        IsCorrect := CheckStringLen(Str);
    Until IsCorrect;
    ReadConsoleString := Str;
End;
Procedure ReadStrings(Var Str1: String; Var Str2: String);
Var
    RF: TextFile;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Вводить строки через файл - ', OPTION1);
    WriteLn('Вводить строки через консоль - ', OPTION2);
    If ChooseOption() = OPTION1 Then
    Begin
        GetFileNormalReading(RF);
        Reset(RF);
        Str1 := ReadFileString(RF);
        Str2 := ReadFileString(RF);
        CloseFile(RF);
    End
    Else
    Begin
        Str1 := ReadConsoleString(1);
        Str2 := ReadConsoleString(2);
    End;
End;
Procedure FillOneAStr(Str: String; Var AStr: TChar);
Var
    I: Integer;
Begin
    SetLength(AStr, Length(Str));
    For I := Low(AStr) To High(AStr) Do
        AStr[I] := Str[I + 1];
End;
Procedure FillAStrs(Str1: String; Str2: String; Var AStr1: TChar; Var AStr2: TChar);
Begin
    FillOneAStr(Str1, Astr1);
    FillOneAStr(Str2, Astr2);
End;
Procedure SortOneAStr(Var AStr: TChar);
Var
    Step: Real;
    I, IStep: Integer;
    Buf: Char;
Begin
    Step := Length(AStr) - 1;
    While Step >= 1 Do
    Begin
        IStep := Trunc(Step);
        I := 0;
        While Step + I < Length(AStr) Do
        Begin 
            If Ord(AStr[I]) > Ord(AStr[I + IStep]) Then
            Begin
                Buf := AStr[I];
                AStr[I] := AStr[I + IStep];
                AStr[I + IStep] := Buf;
            End;
            Inc(I);
        End;
        Step := Step / FACTOR;
    End;
End;
Procedure SortAStrs(Var AStr1: TChar; Var AStr2: TChar);
Begin
    SortOneAStr(AStr1);
    SortOneAStr(AStr2);
End;
Procedure MakeUniqueAStr(Var UniqueAStr: TChar; AStr1: TChar; AStr2: TChar);
Var
    I, J, PreviousMaxIndex: Integer;
Begin
    SetLength(UniqueAStr, Length(AStr1) + Length(AStr2));
    J := 0;
    PreviousMaxIndex := High(AStr1) - 1;
    For I := Low(AStr1) To PreviousMaxIndex Do
        If AStr1[I] <> AStr1[I + 1] Then
        Begin
            UniqueAStr[J] := AStr1[I];
            Inc(J);
        End;
    UniqueAStr[J] := AStr1[High(AStr1)];
    Inc(J);
    PreviousMaxIndex := High(AStr2) - 1;
    For I := Low(AStr2) To PreviousMaxIndex Do
        If AStr2[I] <> AStr2[I + 1] Then
        Begin
            UniqueAStr[J] := AStr2[I];
            Inc(J);
        End;
    UniqueAStr[J] := AStr2[High(AStr2)];
    SetLength(UniqueAStr, J + 1);
End;
Procedure FindSame(UniqueAStr: TChar; Var ResAStr: TChar);
Var
    I, J: Integer;
Begin
    I := 0;
    J := 0;
    SetLength(ResAStr, Length(UniqueAStr));
    While I < High(UniqueAStr) Do
    Begin
        If UniqueAStr[I] = UniqueAStr[I + 1] Then
        Begin
            ResAStr[J] := UniqueAStr[I];
            Inc(J);
        End;
        Inc(I);
    End;
    If J = 0 Then
        SetLength(ResAStr, 1)
    Else
        SetLength(ResAStr, J);
End;
Procedure FindUnique(UniqueAStr: TChar; Var ResAStr: TChar);
Var
    I, J: Integer;
Begin
    I := 0;
    J := 0;
    SetLength(ResAStr, Length(UniqueAStr));
    While I < High(UniqueAStr) Do
    Begin
        If UniqueAStr[I] <> UniqueAStr[I + 1] Then
        Begin
            ResAStr[J] := UniqueAStr[I];
            Inc(J);
        End
        Else
            Inc(I);
        Inc(I);
    End;
    If J = 0 Then
        SetLength(ResAStr, 1)
    Else If (UniqueAStr[High(UniqueAStr)] <> UniqueAStr[High(UniqueAStr) - 1]) Then
    Begin
        ResAStr[J] := UniqueAStr[High(UniqueAStr)];
        SetLength(ResAStr, J + 1);
    End
    Else
        SetLength(ResAStr, J);    
End;
Function ChooseAction() : Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Найти одинаковые символы в обеих строках - ', OPTION1);
    WriteLn('Найти уникальные символы в обеих строках - ', OPTION2);
    ChooseAction := ChooseOption();
End;
Procedure PrintConsoleResult(ResAStr: TChar);
Var
    I: Integer;
Begin
    I := 0;
    WriteLn;
    Write('Элементы, удовлетворяющие условию: ');
    If ResAStr[0] = #0 Then
        Write('элементов, удовлетворяющих условию, нет!')
    Else
        While I < Length(ResAStr) Do
        Begin
            Write('''', ResAStr[I], '''; ');
            Inc(I);
        End;
End;
Procedure PrintFileResult(Var F: TextFile; ResAStr: TChar);
Var
    I: Integer;
Begin
    Append(F);
    I := 0;
    WriteLn(F);
    Write(F, 'Элементы, удовлетворяющие условию: ');
    If ResAStr[0] = #0 Then
        Write(F, 'элементов, удовлетворяющих условию, нет')     
    Else
        While I < Length(ResAStr) Do
        Begin
            Write(F, '''', ResAStr[I], '''; ');
            Inc(I);
        End;
    CloseFile(F);
End;
Procedure PrintResult(ResAStr: TChar);
Var
    WF: TextFile;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Выводить строки через файл - ', OPTION1);
    WriteLn('Выводить строки через консоль - ', OPTION2);
    If ChooseOption() = OPTION1 Then
    Begin
        GetFileNormalWriting(WF);
        PrintFileResult(WF, ResAStr);
    End
    Else
        PrintConsoleResult(ResAStr);
End;
Procedure FreeMemory(Var AStr1: TChar; Var AStr2: TChar; Var UniqueAStr: TChar; Var ResAStr: TChar);
Begin
    AStr1 := Nil;
    AStr2 := Nil;
    UniqueAStr := Nil;
    ResAStr := Nil;       
End;
Var
    AStr1, AStr2, UniqueAStr, ResAStr: TChar;
    Str1, Str2: String;
    Action: Integer;
Begin
    Str1 := '';
    Str2 := '';
    PrintTask();
    ReadStrings(Str1, Str2);
    FillAStrs(Str1, Str2, AStr1, AStr2);
    SortAStrs(AStr1, AStr2);
    MakeUniqueAStr(UniqueAStr, AStr1, AStr2);
    SortOneAStr(UniqueAStr);
    Action := ChooseAction();
    If Action = OPTION1 Then
        FindSame(UniqueAStr, ResAStr)
    Else
        FindUnique(UniqueAStr, ResAStr);
    PrintResult(ResAStr);
    FreeMemory(AStr1, AStr2, UniqueAStr, ResAStr);
    ReadLn;
End.
