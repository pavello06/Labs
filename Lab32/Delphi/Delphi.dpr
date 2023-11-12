Program Lab32;
Uses
  System.SysUtils;
Type
    TSet = Set Of AnsiChar;
    ERRORS_CODE = (CORRECT,
                   INCORRECT_SET_LENGTH,
                   INCORRECT_SET_El,
                   INCORRECT_CHOICE,
                   IS_NOT_TXT,
                   IS_NOT_EXIST,
                   IS_NOT_READABLE,
                   IS_NOT_WRITEABLE,
                   INCORRECT_SET_AMOUNT);
Const
    MIN_S = 1;
    MAX_S = 85;
    AMOUNT_S = 3;
    ERRORS: Array [1 .. 8] Of String = ('Длина множества не попадает в диапазон!',
                                        'Элементы множества разделяются пробелом!',
                                        'Некорректный выбор!',
                                        'Расширение файла не .txt!',
                                        'Проверьте корректность ввода пути к файлу!',
                                        'Файл закрыт для чтения!',
                                        'Файл закрыт для записи!',
                                        'Неправильное число множеств в файле');
Procedure PrintTask();
Begin
    WriteLn('Данная программа находит числа в множестве.'#13#10);
End;
Function IsCorrectSetLen(SSetEl: String) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    SetLen: Integer;
Begin
    Error := CORRECT;
    SetLen := (Length(SSetEl) + 1) Div 2;
    If (SetLen < MIN_S) Or (SetLen > MAX_S) Then
        Error := INCORRECT_SET_LENGTH;
    IsCorrectSetLen := Error;
End;
Function IsCorrectSetEl(SSetEl: String) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    I: Integer;
Begin
    Error := CORRECT;
    I := 2;
    While (Error = CORRECT) And (I <= Length(SSetEl)) Do
    Begin
        If SSetEl[I] <> ' ' Then
            Error := INCORRECT_SET_El;
        Inc(I, 2);
    End;
    IsCorrectSetEl := Error;
End;
Procedure FillSet(SSetEl: String; SetEl: TSet);
Var
    I: Integer;
Begin
    I := 1;
    While I <= Length(SSetEl) Do
    Begin
        Include(SetEl, AnsiChar(SSetEl[I]));
        Inc(I, 2);
    End;
End;
Function ChooseOption(Amount: Integer) : Integer;
Var
    Error: ERRORS_CODE;
    SOption: String;
    IOption: Integer;
Begin
    IOption := 1;
    Repeat
        Error := CORRECT;
        ReadLn(SOption);
        Try
            IOption := StrToInt(SOption)
        Except
            Error := INCORRECT_CHOICE;
        End;
        If (Error = CORRECT) And ((IOption < 1) Or (IOption > Amount)) Then
            Error := INCORRECT_CHOICE;
        If Error <> CORRECT Then
            WriteLn(ERRORS[Ord(Error)], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
    ChooseOption := IOption;
End;
Function GetPartStr(Str: String; PosStart, PosEnd: Integer) : String;
Var
    PartStr: String;
    I: Integer;
Begin
    partStr := '';
    For I := PosStart To PosEnd Do
        PartStr := PartStr + Str[I];
    GetPartStr := PartStr;
End;
Function IsFileTXT(PathToFile: String) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := CORRECT;
    If (Length(PathToFile) < 5) Or (GetPartStr(PathToFile, Length(PathToFile) - 3, Length(PathToFile)) <> '.txt') Then
        Error := IS_NOT_TXT;
    IsFileTXT := Error;
End;
Function IsExist(PathToFile: String) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := CORRECT;
    If Not FileExists(PathToFile) Then
        Error := IS_NOT_EXIST;
    IsExist := Error;
End;
Function IsReadable(Var F: TextFile) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := CORRECT;
    Try
        Reset(F);
        CloseFile(F);
    Except
        Error := IS_NOT_READABLE;
    End;
    IsReadable := Error;
End;
Function IsWriteable(Var F: TextFile) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := CORRECT;
    Try
        Append(F);
        CloseFile(F);
    Except
        Error := Is_NOT_WRITEABLE;
    End;
    IsWriteable := Error;
End;
Function IsCorrectSetAmount(Var F: TextFile) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    Count, StopAmount: Integer;
Begin
    Error := CORRECT;
    Count := 0;
    StopAmount := AMOUNT_S + 1;
    Reset(F);
    While (Not EOF(F)) And (Count <> StopAmount) Do
    Begin
        ReadLn(F);
        Inc(Count);
    End;
    CloseFile(F);
    If Count <> AMOUNT_S Then
        Error := INCORRECT_SET_AMOUNT;
    IsCorrectSetAmount := Error;
End;
Function IsCorrectFileSet(Var F: TextFile) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    SSetEl: String;
Begin
    Error := IsCorrectSetAmount(F);
    Reset(F);
    While (Error = CORRECT) And (Not EOF(F)) Do
    Begin
        ReadLn(F, SSetEl);
        Error := IsCorrectSetLen(SSetEl);
        If Error = CORRECT Then
            Error := IsCorrectSetEl(SSetEl);
    End;
    CloseFile(F);
    IsCorrectFileSet := Error;
End;
Procedure GetFileNormalReading(Var F: TextFile);
Var
    Error: ERRORS_CODE;
    PathToFile: String;
Begin
    Write('Введите путь к файлу с расширением .txt с тремя множествами, с длинами[', MIN_S, '; ', MAX_S, ']: '#13#10);
    Repeat
        ReadLn(PathToFile);
        Error := IsFileTXT(PathToFile);
        If Error = CORRECT Then
            Error := IsExist(PathToFile);
        If Error = CORRECT Then
            AssignFile(F, PathToFile);
        If Error = CORRECT Then
            Error := IsReadable(F);
        If Error = CORRECT Then
            Error := IsCorrectFileSet(F);
        If Error <> CORRECT Then
            WriteLn(ERRORS[Ord(Error)], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
End;
Procedure GetFileNormalWriting(Var F: TextFile);
Var
    Error: ERRORS_CODE;
    PathToFile: String;
Begin
    Write('Введите путь к файлу с расширением .txt для получения результата: '#13#10);
    Repeat
        ReadLn(PathToFile);
        Error := IsFileTXT(PathToFile);
        If Error = CORRECT Then
            Error := IsExist(PathToFile);
        If Error = CORRECT Then
            AssignFile(F, PathToFile);
        If Error = CORRECT Then
            Error := IsWriteable(F);
        If Error <> CORRECT Then
            WriteLn(ERRORS[Ord(Error)], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
End;
Procedure ReadFileSet(Var F: TextFile; SetEl: TSet);
Var
    SSetEl: String;
Begin
    ReadLn(F, SSetEl);
    FillSet(SSetEl, SetEl);
End;
Function IsCorrectConsoleSet(SSetEl: String) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := IsCorrectSetLen(SSetEl);
    If Error = CORRECT Then
        Error := IsCorrectSetEl(SSetEl);
    IsCorrectConsoleSet := Error;
End;
Procedure ReadConsoleSet(Num: Integer; SetEl: TSet);
Var
    Error: ERRORS_CODE;
    SSetEl: String;
Begin
    Write('Введите множество Х', Num, ' через пробелы: ');
    Repeat
        ReadLn(SSetEl);
        Error := IsCorrectConsoleSet(SSetEl);
        If Error <> CORRECT Then
            Write(ERRORS[Ord(Error)], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
    FillSet(SSetEl, SetEl);
End;
Procedure ReadSets(Var X1: TSet; Var X2: TSet; Var X3: TSet);
Var
    RF: TextFile;
    Option: Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Вводить множества через файл - 1');
    WriteLn('Вводить множества через консоль - 2');
    Option := ChooseOption(2);
    If Option = 1 Then
    Begin
        GetFileNormalReading(RF);
        Reset(RF);
        ReadFileSet(RF, X1);
        ReadFileSet(RF, X2);
        ReadFileSet(RF, X3);
        CloseFile(RF);
    End
    Else
    Begin
        ReadConsoleSet(1, X1);
        ReadConsoleSet(2, X2);
        ReadConsoleSet(3, X3);
    End;
End;
Procedure UniteSets(Var Y: TSet; X1: TSet; X2: TSet; X3: TSet);
Begin
    Y := X1 + X2 + X3;
End;
Procedure FindNums(Var Y1: TSet; Y: TSet);
Var
    Ch: AnsiChar;
Begin
    For Ch in Y Do
        If (Ch >= '0') And (Ch <= '9') Then
            Include(Y1, Ch);
End;
Procedure PrintConsoleResult(Y1: TSet);
Var
    Ch: AnsiChar;
Begin
    Write(#13#10'Цифры в множестве: ');
    If Y1 = [] Then
        Write('цифр в множестве нет')
    Else
        For Ch in Y1 Do
            Write('''', Ch, '''; ');
End;
Procedure PrintFileResult(Var F: TextFile; Y1: TSet);
Var
    Ch: AnsiChar;
Begin
    Append(F);
    Write(F, #13#10'Цифры в множестве: ');
    If Y1 = [] Then
        Write(F, 'цифр в множестве нет')
    Else
        For Ch in Y1 Do
            Write(F, '''', Ch, '''; ');
    CloseFile(F);
End;
Procedure PrintResult(Y1: TSet);
Var
    WF: TextFile;
    Option: Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Выводить множество через файл - 1');
    WriteLn('Выводить множество через консоль - 2');
    Option := ChooseOption(2);
    If Option = 1 Then
    Begin
        GetFileNormalWriting(WF);
        PrintFileResult(WF, Y1);
    End
    Else
        PrintConsoleResult(Y1);
End;
Var
    X1, X2, X3, Y, Y1: TSet;
Begin
    PrintTask();
    ReadSets(X1, X2, X3);
    UniteSets(Y, X1, X2, X3);
    FindNums(Y1, Y);
    PrintResult(Y1);
    ReadLn;
End.
