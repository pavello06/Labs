Program Lab22;

Uses
    System.SysUtils;

Const
    MIN_P = 1;
    MAX_P = 1000000000;
    DIGIT = 10;
Var
    P: Integer;

Procedure OutputTask;
Begin
    Writeln('Данная программа подсчитывает сумму цифр введённого натурального числа.');
    Writeln;
End;

Procedure Area(MIN, MAX: Integer);
Begin
    Write('Введите натуральное чиcло P[', MIN, '; ', MAX, ']: ');
End;

Procedure ErrorOfArea();
Begin
    Writeln('Значение не попадает в диапазон!');
End;

Procedure ErrorOfType();
Begin
    Writeln('Проверьте корректность ввода данных!');
End;

Function Check(MIN, MAX: Integer) : Integer;
Var
    Num: Integer;
    IsIncorrect: Boolean;

Begin
    IsIncorrect := True;
    While (IsIncorrect) Do
    Begin
        Area(MIN, MAX);
        Try
            Readln(Num);
            IsIncorrect := False;
        Except
            ErrorOfType();
        End;
        If (Not IsIncorrect And ((Num < MIN) Or (Num > MAX))) Then
        Begin
            ErrorOfArea();
            IsIncorrect := True;
        End;
    End;
    Check := Num;
End;

Function CalcSumOfNums(Num : Integer) : Integer;
Var
    Sum: Integer;

Begin
    Sum := 0;
    While (Num > 0) Do
    Begin
        Sum := Sum + Num Mod 10;
        Num := Num Div DIGIT;
    End;
    CalcSumOfNums := Sum;
End;

Procedure OutputRes(Sum: Integer);
Begin
    Writeln;
    Writeln('Сумма цифр числа: ', Sum);
End;

Begin
    OutputTask();
    P := Check(MIN_P, MAX_P);
    OutputRes(CalcSumOfNums(P));
    Readln;
End.
