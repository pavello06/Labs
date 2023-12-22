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
    ERRORS: Array [ERRORS_CODE] Of String = ( '',
                                              'Длина множества не попадает в диапазон!',
                                              'Элементы множества разделяются пробелом!',
                                              'Некорректный выбор!',
                                              'Расширение файла не .txt!',
                                              'Проверьте корректность ввода пути к файлу!',
                                              'Файл закрыт для чтения!',
                                              'Файл закрыт для записи!',
                                              'Неправильное число множеств в файле');
Procedure PrintTask();
Begin
    WriteLn('Данная программа формирует множество из трёх множеств и находит числа в этом множестве.');
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
Function IsCorrectSet(SSetEl: String) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := IsCorrectSetLen(SSetEl);
    If Error = CORRECT Then
        Error := IsCorrectSetEl(SSetEl);
    IsCorrectSet := Error;
End;
Procedure FillSet(SSetEl: String; Var SetEl: TSet);
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
            WriteLn(ERRORS[Error], #13#10'Повторите попытку: ');
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
        Try
            Reset(F);
        Finally
            CloseFile(F);
        End;
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
        Try
            Append(F);
        Finally
            CloseFile(F);
        End;
    Except
        Error := Is_NOT_WRITEABLE;
    End;
    IsWriteable := Error;
End;
Procedure GetFileNormalReading(Var F: TextFile);
Var
    Error: ERRORS_CODE;
    PathToFile: String;
Begin
    Repeat
        ReadLn(PathToFile);
        Error := IsFileTXT(PathToFile);
        If Error = CORRECT Then
            Error := IsExist(PathToFile);
        If Error = CORRECT Then
            AssignFile(F, PathToFile);
        If Error = CORRECT Then
            Error := IsReadable(F);
        If Error <> CORRECT Then
            WriteLn(ERRORS[Error], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
End;
Procedure GetFileNormalWriting(Var F: TextFile);
Var
    Error: ERRORS_CODE;
    PathToFile: String;
Begin
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
            WriteLn(ERRORS[Error], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
End;
Function ReadFileSet(Var F: TextFile; Var SetEl: TSet) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    SSetEl: String;
Begin
    ReadLn(F, SSetEl);
    Error := IsCorrectSet(SSetEl);
    FillSet(SSetEl, SetEl);
    ReadFileSet := Error;
End;
Procedure ReadFileSets(Var X1: TSet; Var X2: TSet; Var X3: TSet);
Var
    Error: ERRORS_CODE;
    F: TextFile;
Begin
    WriteLn('Введите путь к файлу с расширением .txt с тремя множествами, с длинами[', MIN_S, '; ', MAX_S, ']: ');
    Repeat
        GetFileNormalReading(F);
        Error := CORRECT;
        Reset(F);
        If Not EOF(F) Then
            Error := ReadFileSet(F, X1)
        Else
            Error := INCORRECT_SET_AMOUNT;
        If Not EOF(F) Then
        Begin
            If Error = CORRECT Then
                Error := ReadFileSet(F, X2);
        End
        Else
            Error := INCORRECT_SET_AMOUNT;
        If Not EOF(F) Then
        Begin
            If Error = CORRECT Then
                Error := ReadFileSet(F, X3);
        End
        Else
            Error := INCORRECT_SET_AMOUNT;
        If (Error = CORRECT) And Not EOF(F) Then
            Error := INCORRECT_SET_AMOUNT;
        CloseFile(F);
        If Error <> CORRECT Then
            WriteLn(ERRORS[Error], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
End;
Procedure ReadConsoleSet(Num: Integer; Var SetEl: TSet);
Var
    Error: ERRORS_CODE;
    SSetEl: String;
Begin
    Write('Введите множество Х', Num, ' через пробелы: ');
    Repeat
        ReadLn(SSetEl);
        Error := IsCorrectSet(SSetEl);
        If Error <> CORRECT Then
            Write(ERRORS[Error], #13#10'Повторите попытку: ');
    Until Error = CORRECT;
    FillSet(SSetEl, SetEl);
End;
Procedure ReadConsoleSets(Var X1: TSet; Var X2: TSet; Var X3: TSet);
Begin
    ReadConsoleSet(1, X1);
    ReadConsoleSet(2, X2);
    ReadConsoleSet(3, X3);
End;
Procedure ReadSets(Var X1: TSet; Var X2: TSet; Var X3: TSet);
Var
    Option: Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Вводить множества через файл - 1');
    WriteLn('Вводить множества через консоль - 2');
    Option := ChooseOption(2);
    If Option = 1 Then
        ReadFileSets(X1, X2, X3)
    Else
        ReadConsoleSets(X1, X2, X3);
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
Procedure PrintConsoleResult(Y1: TSet; Y: TSet);
Var
    Ch: AnsiChar;
Begin
    Write(#13#10'Множество Y = {X1 U X2 U X3}: ');
    For Ch in Y Do
            Write('''', Ch, '''; ');
    Write(#13#10'Цифры в множестве: ');
    If Y1 = [] Then
        Write('цифр в множестве нет')
    Else
        For Ch in Y1 Do
            Write('''', Ch, '''; ');
End;
Procedure PrintFileResult(Y1: TSet; Y: TSet);
Var
    F: TextFile;
    Ch: AnsiChar;
Begin
    WriteLn('Введите путь к файлу с расширением .txt для получения результата: ');
    GetFileNormalWriting(F);
    Append(F);
    Write(F, #13#10'Множество Y = {X1 U X2 U X3}: ');
    For Ch in Y Do
            Write(F, '''', Ch, '''; ');
    Write(F, #13#10'Цифры в множестве: ');
    If Y1 = [] Then
        Write(F, 'цифр в множестве нет')
    Else
        For Ch in Y1 Do
            Write(F, '''', Ch, '''; ');
    CloseFile(F);
End;
Procedure PrintResult(Y1: TSet; Y: TSet);
Var
    Option: Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Выводить множества через файл - 1');
    WriteLn('Выводить множества через консоль - 2');
    Option := ChooseOption(2);
    If Option = 1 Then
        PrintFileResult(Y1, Y)
    Else
        PrintConsoleResult(Y1, Y);
End;
Var
    X1, X2, X3, Y, Y1: TSet;
Begin
    PrintTask();
    ReadSets(X1, X2, X3);
    UniteSets(Y, X1, X2, X3);
    FindNums(Y1, Y);
    PrintResult(Y1, Y);
    ReadLn;
End.
