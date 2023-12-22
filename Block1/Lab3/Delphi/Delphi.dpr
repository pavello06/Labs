Program Project3;

Uses
  System.SysUtils;

Const
    MIN_EPS = 0;
    MAX_EPS = 1;
    MIN_X = -1000;
    MAX_X = 1000;
Var
    EPS, X, Num, Sin: Real;
    I, J, N: Integer;
    IsIncorrect: Boolean;

Begin
    EPS := 0;
    X := 0;
    Writeln('Данная программа считает значение функции f(x) = sin^2(x) для введённого значения X, а также подсчитывает количество чисел из ряда Маклорена больших EPS.');
    Writeln;

    IsIncorrect := True;
    While (IsIncorrect) Do
    Begin
        Write('Введите EPS (0; 1): ');
        Try
            Readln(EPS);
            IsIncorrect := False;
        Except
            Writeln('Проверьте корректность ввода данных!');
        End;
        If (Not IsIncorrect And ((EPS <= MIN_EPS) Or (EPS >= MAX_EPS))) Then
        Begin
            Writeln('Значение не попадает в диапазон!');
            IsIncorrect := True;
        End;
    End;
    IsIncorrect := True;
    While (IsIncorrect) Do
    Begin
        Write('Введите X(-1000; 1000): ');
        Try
            Readln(X);
            IsIncorrect := False;
        Except
            Writeln('Проверьте корректность ввода данных!');
        End;
        If (Not IsIncorrect And ((X <= MIN_X) Or (X >= MAX_X))) Then
        Begin
            Writeln('Значение не попадает в диапазон!');
            IsIncorrect := True;
        End;
    End;

    Sin := 0;
    N := 0;
    Num := 0;

    J := 1;
    While (J = 1) Do
    Begin
        Sin := Sin + Num;
        Num := 1;
        N := N + 1;
        For I := 1 To N - 1 Do
            Num := Num * (-1);
        For I := 1 To 2 * N - 1 Do
            Num := Num * 2;
        For I := 1 To 2 * N Do
            Num := Num * X;
        For I := 1 To 2 * N Do
            Num := Num / I;

        If (Num < 0) Then
        Begin
            If (-Num < EPS) Then
                J := 0;
        End
        Else
        Begin
            If (Num < EPS) Then
                J := 0;
        End;
    End;

    Writeln('Sin: ', Sin, '; N: ', N);

    Readln;
End.
