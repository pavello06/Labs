Program Lab24;
Uses
  System.SysUtils;
Type
    TMatrix = Array Of Array Of Integer;
    TArr = Array Of Integer;
Const
    MIN_O = 1;
    MAX_O = 10;
    MIN_MAT = 1;
    MAX_MAT = 1000000;
    YES = 1;
    NO = 2;
Procedure PrintTask();
Begin
    WriteLn('Данная программа подсчитывает число повторяющихся элементов в матрице.');
    WriteLn;
End;
Function ChooseFileIOput() : Boolean;
Var
    IsFileInput: Integer;
    IsCorrect, Choose: Boolean;
Begin
    IsFileInput := 0;
    IsCorrect := False;
    Choose := False;
    Repeat
        Try
            ReadLn(IsFileInput);
            IsCorrect := True;
        Except
            WriteLn('Некорректный выбор!');
            Write('Повторите попытку: ');
            IsCorrect := False;
        End;
        If IsCorrect Then
        Begin
            If IsFileInput = YES Then
                Choose := True
            Else If IsFileInput = NO Then
                Choose := False
            Else
            Begin
                WriteLn('Некорректный выбор!');
                Write('Повторите попытку: ');
                IsCorrect := False;
            End;
        End;
    Until IsCorrect;
    ChooseFileIOput := Choose;
End;
Function CheckArea(Num: Integer; Const MIN, MAX: Integer) : Boolean;
Var
    IsCorrect: Boolean;
Begin
    IsCorrect := True;
    If (Num < MIN) Or (Num > MAX) Then
    Begin
        Writeln('Значение не попадает в диапазон!');
        IsCorrect := False;
    End;
    CheckArea := IsCorrect;
End;
Function ReadPathToFile() : String;
Var
    PathToFile: String;
    IsCorrect: Boolean;
Begin
    PathToFile := '';
    IsCorrect := False;
    Repeat
        Write('Введите путь к файлу с расширением .txt с матрицей, у которой разряд не должен превышать ', MAX_O, ', а её натуральные элементы должны лежать в пределе[', MIN_MAT, ': ', MAX_MAT,']: ');
        ReadLn(PathToFile);
        If ExtractFileExt(PathToFile) = '.txt' Then
            IsCorrect := True
        Else
        Begin
            WriteLn('Расширение файла не .txt!');
            IsCorrect := False;
        End;
    Until IsCorrect;
    ReadPathToFile := PathToFile;
End;
Function IsExists(PathToFile: String) : Boolean;
Var
    IsCorrect: Boolean;
Begin
    IsCorrect := False;
    If FileExists(PathToFile) Then
        IsCorrect := True;
    IsExists := IsCorrect;
End;
Function IsAbleToReading(Var F: TextFile) : Boolean;
Var
    IsCorrect: Boolean;
Begin
    IsCorrect := True;
    Try
        Reset(F);
        CloseFile(F);
    Except
        IsCorrect := False;
    End;
    IsAbleToReading := IsCorrect;
End;
Function IsAbleToWriting(PathToFile: String) : Boolean;
Var
    IsCorrect: Boolean;
Begin
    IsCorrect := True;
    If FileIsReadOnly(PathToFile) Then
        IsCorrect := False;
    IsAbleToWriting := IsCorrect;
End;
Function IsEmpty(Var F: TextFile) : Boolean;
Var
    Size: Integer;
    IsCorrect: Boolean;
Begin
    Size := 0;
    IsCorrect := False;
    Reset(F);
    If Not EOF(F) Then
        Size := 1;
    CloseFile(F);
    If Size = 0 Then
        IsCorrect := True;
    IsEmpty := IsCorrect;
End;
Function IsRightFileNums(Var F: TextFile) : Boolean;
Var
    Buf: Char;
    K: Integer;
    IsCorrect: Boolean;
Begin
    Buf := ' ';
    K := 0;
    IsCorrect := True;
    Reset(F);
    Try
        Read(F, K);
    Except
        IsCorrect := False;
    End;
    ReadLn(F, Buf);
    If Buf <> #13 Then
        IsCorrect := False;
    If IsCorrect Then
        IsCorrect := CheckArea(K, MIN_O, MAX_O);
    While IsCorrect And Not EOF(F) Do
    Begin
        While IsCorrect And Not EOLN(F) Do
        Begin
            Try
                Read(F, K);
            Except
                IsCorrect := False;
            End;
            If IsCorrect Then
                IsCorrect := CheckArea(K, MIN_MAT, MAX_MAT);
        End;
        ReadLn(F);
    End;
    CloseFile(F);
    IsRightFileNums := IsCorrect;
End;
Function IsOrdersEqual(Var F: TextFile) : Boolean;
Var
    Order, Rows, Cols, K: Integer;
    IsCorrect: Boolean;
Begin
    Order := 0;
    Rows := 0;
    Cols := 0;
    K := 0;
    IsCorrect := True;
    Reset(F);
    Readln(F, Order);
    While IsCorrect And Not EOF(F) Do
    Begin
      Cols := 0;
      While IsCorrect And Not EOLN(F) Do
      Begin
          Read(F, K);
          Inc(Cols);
          IsCorrect := CheckArea(Cols, MIN_O, MAX_O);
      End;
      If IsCorrect Then
      Begin
          Readln(F);
          Inc(Rows);
          IsCorrect := CheckArea(Rows, MIN_O, MAX_O);
          If Cols <> Order Then
              IsCorrect := False;
      End;
    End;
    CloseFile(F);
    If IsCorrect And (Not Rows = Order) Then
        IsCorrect := False;
    IsOrdersEqual := IsCorrect;
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
        If Not IsExists(PathToFile) Then
        Begin
            IsCorrect := False;
            Writeln('Проверьте корректность ввода пути к файлу!');
        End;
        If IsCorrect Then
            AssignFile(F, PathToFile);
        If IsCorrect And Not IsAbleToReading(F) Then
        Begin
            IsCorrect := False;
            Writeln('Файл закрыт для чтения!');
        End;
        If IsCorrect And IsEmpty(F) Then
        Begin
            IsCorrect := False;
            WriteLn('Файл пуст!');
        End;
        If IsCorrect And Not IsRightFileNums(F) Then
        Begin
            IsCorrect := False;
            WriteLn('Некорректный тип данных внутри файла!');
        End;
        If IsCorrect And Not IsOrdersEqual(F) Then
        Begin
            IsCorrect := False;
            Writeln('Значения порядков не равны!');
        End;
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
        AssignFile(F, PathToFile);
        If Not IsExists(PathToFile) Then
        Begin
            IsCorrect := False;
            Writeln('Проверьте корректность ввода пути к файлу!');
        End;
        If IsCorrect And Not IsAbleToWriting(PathToFile) Then
        Begin
            IsCorrect := False;
            WriteLn('Файл закрыт для записи!');
        End;
    Until IsCorrect;
End;
Function ReadFileOrder(Var F: TextFile) : Integer;
Var
    Order: Integer;
Begin
    Order := 0;
    Reset(F);
    Read(F, Order);
    CloseFile(F);
    ReadFileOrder := Order;
End;
Function ReadFileMatrix(Var F: TextFile; Order: Integer) : TMatrix;
Var
    Matrix: TMatrix;
    Row, Col: Integer;
Begin
    SetLength(Matrix, Order, Order);
    Reset(F);
    Readln(F);
    For Row := Low(Matrix) To High(Matrix) Do
    Begin
        For Col := Low(Matrix[Row]) To High(Matrix[Row]) Do
            Read(F, Matrix[Row][Col]);
        Readln(F);
    End;
    CloseFile(F);
    ReadFileMatrix := Matrix
End;
Function ReadConsoleOrder() : Integer;
Var
    Order: Integer;
    IsCorrect: Boolean;
Begin
    Order := 0;
    IsCorrect := False;
    Repeat
        Write('Введите порядок матрицы Order[', MIN_O, '; ', MAX_O, ']: ');
        Try
            Readln(Order);
            IsCorrect := True;
        Except
            Writeln('Проверьте корректность ввода данных!');
            IsCorrect := False;
        End;
        If IsCorrect Then
            IsCorrect := CheckArea(Order, MIN_O, MAX_O);
    Until IsCorrect;
    ReadConsoleOrder := Order;
End;
Function ReadConsoleMatrix(Order: Integer) : TMatrix;
Var
    Matrix: TMatrix;
    Row, Col: Integer;
    IsCorrect: Boolean;
Begin
    IsCorrect := False;
    SetLength(Matrix, Order, Order);
    For Row := Low(Matrix) To High(Matrix) Do
        For Col := Low(Matrix) To High(Matrix) Do
            Repeat
                Write('Введите в ', (Row + 1), ' строке ', (Col + 1), ' столбце натуральный элемент[', MIN_MAT, '; ', MAX_MAT,  ']: ');
                Try
                    Readln(Matrix[Row][Col]);
                    IsCorrect := True;
                Except
                    Writeln('Проверьте корректность ввода данных!');
                    IsCorrect := False;
                End;
                If IsCorrect Then
                    IsCorrect := CheckArea(Matrix[Row][Col], MIN_MAT, MAX_MAT);
            Until IsCorrect;
    ReadConsoleMatrix := Matrix;
End;
Procedure ReadMatrix(Var Matrix: TMatrix; Var Order: Integer);
Var
    RF: TextFile;
Begin
    WriteLn('Вы хотите вводить матрицу через файл? (Да - ', YES, ' / Нет - ', NO, ')');
    If ChooseFileIOput() Then
    Begin
        GetFileNormalReading(RF);
        Order := ReadFileOrder(RF);
        Matrix := ReadFileMatrix(RF, Order);
    End
    Else
    Begin
        Order := ReadConsoleOrder();
        Matrix := ReadConsoleMatrix(Order);
    End;
End;
Procedure FillArr(Matrix: TMatrix; Order: Integer; Var Arr: TArr; Var ArrLen: Integer);
Var
    I, Row, Col: Integer;
Begin
    I := 0;
    ArrLen := Order * Order;
    SetLength(Arr, ArrLen);
    For Row := Low(Matrix) To High(Matrix) Do
        For Col := Low(Matrix) To High(Matrix) Do
        Begin
            Arr[I] := Matrix[Row][Col];
            Inc(I);
        End;
End;
Procedure SortArr(Var Arr: TArr; ArrLen: Integer);
Var
    I, J, PreviousMaxIndex, Buf: Integer;
Begin
    PreviousMaxIndex := ArrLen - 2;
    For I := Low(Arr) To High(Arr) Do
        For J := Low(Arr) To PreviousMaxIndex - I Do
            If Arr[J] > Arr[J + 1] Then
            Begin
                Buf := Arr[J];
                Arr[J] := Arr[J + 1];
                Arr[J + 1] := Buf;
            End;
End;
Procedure FindSame(Arr: TArr; ArrLen: Integer; Var ResMatrix: TMatrix);
Var
    Count, I, J, PreviousMaxIndex: Integer;
Begin
    Count := 1;
    J := 0;
    PreviousMaxIndex := ArrLen - 2;
    SetLength(ResMatrix, ArrLen, ArrLen);
    For I := Low(Arr) To PreviousMaxIndex Do
        If Arr[I] = Arr[I + 1] Then
            Inc(Count)
        Else
        Begin
            ResMatrix[J][0] := Arr[I];
            ResMatrix[J][1] := Count;
            Inc(J);
            Count:= 1;
        End;
    ResMatrix[J][0] := Arr[ArrLen - 1];
    ResMatrix[J][1] := Count;
End;
Procedure FreeMemory(Var Matrix: TMatrix; Var Arr: TArr; Var ResMatrix: TMatrix);
Begin
    Matrix := Nil;
    Arr := Nil;
    ResMatrix := Nil;
End;
Procedure PrintConsoleResult(ResMatrix: TMatrix; ArrLen: Integer);
Var
    I: Integer;
Begin
    I := 0;
    WriteLn;
    While (I < ArrLen) And (ResMatrix[I][1] <> 0) Do
    Begin
        WriteLn('Число ', ResMatrix[I][0], ' встречается ', ResMatrix[I][1], ' раз(а).');
        Inc(I);
    End;
End;
Procedure PrintFileResult(Var F: TextFile; ResMatrix: TMatrix; ArrLen: Integer);
Var
    I: Integer;
Begin
    Append(F);
    I := 0;
    WriteLn(F);
    While (I < ArrLen) And (ResMatrix[I][1] <> 0) Do
    Begin
        WriteLn(F, 'Число ', ResMatrix[I][0], ' встречается ', ResMatrix[I][1], ' раз(а).');
        Inc(I);
    End;
    CloseFile(F);
End;
Procedure PrintResult(ResMatrix: TMatrix; ArrLen: Integer);
Var
    WF: TextFile;
Begin
    WriteLn('Вы хотите выводить матрицу через файл? (Да - ', YES, ' / Нет - ', NO, ')');
    If ChooseFileIOput() Then
    Begin
        GetFileNormalWriting(WF);
        PrintFileResult(WF, ResMatrix, ArrLen);
    End
    Else
        PrintConsoleResult(ResMatrix, ArrLen);
End;
Var
    Matrix: TMatrix;
    Order: Integer;
    Arr: TArr;
    ArrLen: Integer;
    ResMatrix: TMatrix;
Begin
    Order := 0;
    ArrLen := 0;
    PrintTask();
    ReadMatrix(Matrix, Order);
    FillArr(Matrix, Order, Arr, ArrLen);
    SortArr(Arr, ArrLen);
    FindSame(Arr, ArrLen, ResMatrix);
    PrintResult(ResMatrix, ArrLen);
    FreeMemory(Matrix, Arr, ResMatrix);
    ReadLn;
End.
