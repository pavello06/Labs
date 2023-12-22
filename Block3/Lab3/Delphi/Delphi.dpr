Program Lab33;
Uses
    System.SysUtils;
Type
    TArr = Array Of Integer;
    TPosition = (PointerLeft, PointerRight);
    ERRORS_CODE = (CORRECT,
                   INCORRECT_RANGE,
                   INCORRECT_NUM,
                   INCORRECT_CHOICE,
                   IS_NOT_TXT,
                   IS_NOT_EXIST,
                   IS_NOT_READABLE,
                   IS_NOT_WRITEABLE,
                   INCORRECT_ARR_LEN,
                   EXTRA_DATA);
Const
    MIN_A = -1000000;
    MAX_A = 1000000;
    MIN_L = 1;
    MAX_L = 100;
    ERRORS: Array [ERRORS_CODE] Of String = ( '',
                                              'Значение не попадает в диапазон!',
                                              'Введено не число!',
                                              'Некорректный выбор!',
                                              'Расширение файла не .txt!',
                                              'Проверьте корректность ввода пути к файлу!',
                                              'Файл закрыт для чтения!',
                                              'Файл закрыт для записи!',
                                              'Неправильная длина массива!',
                                              'Лишние данные в файле!');
Procedure PrintTask();
Begin
    WriteLn('Данная программа реализует сортировку двухпутевыми вставками.');
End;
Procedure PrintError(Error: ERRORS_CODE);
Begin
    WriteLn(ERRORS[Error], #13#10'Повторите попытку: ');
End;
Function IsCorrectRange(Num: Integer; Const MIN, MAX: Integer) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := CORRECT;
    If (Num < MIN) Or (Num > MAX) Then
        Error := INCORRECT_RANGE;
    IsCorrectRange := Error;
End;
Function ChooseOption(Quantity: Integer) : Integer;
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
        If (Error = CORRECT) And ((IOption < 1) Or (IOption > Quantity)) Then
            Error := INCORRECT_CHOICE;
        If Error <> CORRECT Then
            PrintError(Error);
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
            PrintError(Error);
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
            PrintError(Error);
    Until Error = CORRECT;
End;
Function ReadFileNum(Var F: TextFile; Var Num: Integer; Const MIN, MAX: Integer) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := CORRECT;
    Try
        Read(F, Num);
    Except
        Error := INCORRECT_NUM;
    End;
    If Error = CORRECT Then
        Error := IsCorrectRange(Num, MIN, MAX);
    ReadFileNum := Error;
End;
Function ReadFileArrLen(Var F: TextFile; Var ArrLen: Integer) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
Begin
    Error := ReadFileNum(F, ArrLen, MIN_L, MAX_L);
    If (Error = CORRECT) And (Not EOLN(F)) Then
        Error := EXTRA_DATA;
    ReadFileArrLen := Error;
End;
Function ReadFileArr(Var F: TextFile; Var Arr: TArr; ArrLen: Integer) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    I: Integer;
Begin
    SetLength(Arr, ArrLen);
    Error := CORRECT;
    I := 0;
    ReadLn(F);
    While (Error = CORRECT) And (I < ArrLen) Do
    Begin
        Error := ReadFileNum(F, Arr[I], MIN_A, MAX_A);
        Inc(I);
    End;
    If (Error = CORRECT) And (I <> ArrLen) Then
        Error := INCORRECT_ARR_LEN;
    If (Error = CORRECT) And (Not EOF(F)) Then
        Error := EXTRA_DATA;
    ReadFileArr := Error;
End;
Function ReadFileData(Var F: TextFile; Var Arr: TArr) : ERRORS_CODE;
Var
    Error: ERRORS_CODE;
    ArrLen: Integer;
Begin
    Reset(F);
    Error := ReadFileArrLen(F, ArrLen);
    If Error = CORRECT Then
        Error := ReadFileArr(F, Arr, ArrLen);
    CloseFile(F);
    ReadFileData := Error
End;
Procedure ReadFile(Var Arr: TArr);
Var
    Error: ERRORS_CODE;
    F: TextFile;
Begin
    WriteLn('Введите путь к файлу с расширением .txt.');
    WriteLn('Содержимое: Массив с длиной[', MIN_L, '; ', MAX_L, '] и элементами в диапазоне[', MIN_A, '; ', MAX_A, ']: ');
    Repeat
        GetFileNormalReading(F);
        Error := ReadFileData(F, Arr);
        If Error <> CORRECT Then
            PrintError(Error);
    Until Error = CORRECT;
End;
Procedure ReadConsoleNum(Var Num: Integer; Const MIN, MAX: Integer);
Var
    Error: ERRORS_CODE;
    SNum: String;
Begin
    Repeat
        Error := CORRECT;
        ReadLn(SNum);
        Try
            Num := StrToInt(SNum);
        Except
            Error := INCORRECT_NUM;
        End;
        If Error = CORRECT Then
            Error := IsCorrectRange(Num, MIN, MAX);
        If Error <> CORRECT Then
            PrintError(Error);
    Until Error = CORRECT;
End;
Procedure ReadConsoleArrLen(Var ArrLen: Integer);
Begin
    WriteLn('Введите длину массива в диапазоне[', MIN_L, ': ', MAX_L, ']: ');
    ReadConsoleNum(ArrLen, MIN_L, MAX_L);
End;
Procedure ReadConsoleArr(Var Arr: TArr; ArrLen: Integer);
Var
    I: Integer;
Begin
    SetLength(Arr, ArrLen);
    Write('Введите массив: '#13#10);
    For I := Low(Arr) To High(Arr) Do
    Begin
        Write('Введите ', I + 1, ' элемент массива в диапазоне[', MIN_A, ': ', MAX_A, ']: ');
        ReadConsoleNum(Arr[I], MIN_A, MAX_A);
    End;
End;
Procedure ReadConsoleData(Var Arr: TArr);
Var
    ArrLen: Integer;
Begin
    ReadConsoleArrLen(ArrLen);
    ReadConsoleArr(Arr, ArrLen);
End;
Procedure ReadConsole(Var Arr: TArr);
Begin
    ReadConsoleData(Arr);
End;
Procedure ReadArr(Var Arr: TArr);
Var
    Option: Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Вводить массив через файл - 1');
    WriteLn('Вводить массив через консоль - 2');
    Option := ChooseOption(2);
    If Option = 1 Then
        ReadFile(Arr)
    Else
        ReadConsole(Arr);
End;
Procedure ShowProcess(HelpArr: TArr);
Var
    I: Integer;
Begin
    For I := Low(HelpArr) To High(HelpArr) Do
        Write(HelpArr[I], ' ');
    Writeln;
End;
Procedure SortArr(Arr: TArr);
Var
    HelpArr: TArr;
    LeftIndex, RightIndex, I, J, CurrentElem: Integer;
Begin
    SetLength(HelpArr, Length(Arr) * 2 - 1);
    HelpArr[High(Arr)] := Arr[0];
    LeftIndex := High(Arr);
    RightIndex := High(Arr);
    For I := 1 To High(Arr) Do
    Begin
        CurrentElem := Arr[I];
        If CurrentElem > Arr[I - 1] Then
        Begin
            Inc(RightIndex);
            J := RightIndex;
            While CurrentElem < HelpArr[J - 1] Do
            Begin
                HelpArr[J] := HelpArr[J - 1];
                Dec(J);
            End;
        End
        Else
        Begin
            Dec(LeftIndex);
            J := LeftIndex;
            While CurrentElem > HelpArr[J + 1] Do
            Begin
                HelpArr[J] := HelpArr[J + 1];
                Inc(J);
            End;
        End;
        HelpArr[J] := CurrentElem;
        ShowProcess(HelpArr);
    End;
    For J := Low(Arr) To High(Arr) Do
        Arr[J] := HelpArr[J + LeftIndex];
End;
Procedure PrintConsoleResult(Arr: TArr);
Var
    I: Integer;
Begin
    WriteLn(#13#10'Отсортированный массив: ');
    For I := Low(Arr) To High(Arr) Do
        Write(Arr[I], ' ');
End;
Procedure PrintFileResult(Arr: TArr);
Var
    F: TextFile;
    I: Integer;
Begin
    WriteLn('Введите путь к файлу с расширением .txt для получения результата: ');
    GetFileNormalWriting(F);
    Append(F);
    WriteLn(F, #13#10'Отсортированный массив: ');
    For I := Low(Arr) To High(Arr) Do
        Write(F, Arr[I], ' ');
    CloseFile(F);
End;
Procedure PrintResult(Arr: TArr);
Var
    Option: Integer;
Begin
    WriteLn('Вы хотите: ');
    WriteLn('Выводить массив через файл - 1');
    WriteLn('Выводить массив через консоль - 2');
    Option := ChooseOption(2);
    If Option = 1 Then
        PrintFileResult(Arr)
    Else
        PrintConsoleResult(Arr);
End;
Var
    Arr: TArr;
Begin
    PrintTask();
    ReadArr(Arr);
    SortArr(Arr);
    PrintResult(Arr);
    ReadLn;
End.
