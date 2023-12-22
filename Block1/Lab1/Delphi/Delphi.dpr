Program Lab11;
uses
  System.SysUtils;

Const
    MIN_R = 1;
    MIN_XY = -1000000;
    MAX_ALL = 1000000;
    EPS = 0.0000001;
Var
    R, X, Y: Real;
    OnCircle: Char;
    IsCorrect: Boolean;
Begin
    R := 0;
    X := 0;
    Y := 0;
    IsCorrect := True;
    Writeln('Данная программа проверяет, находится ли точка с координатами (X; Y) на окружности радиусом R с центром в начале координат.');
    Writeln;
    Repeat
        Write('Введите радиус окружности R[1; 100000]: ');
        Try
            Readln(R);
            IsCorrect := True;
        Except
            Writeln('Проверьте корректность ввода данных!');
            IsCorrect := False;
        End;
        If (IsCorrect And ((R < MIN_R) Or (R > MAX_ALL))) Then
        Begin
            Writeln('Значение не попадает в диапазон!');
            IsCorrect := False;
        End;
    Until IsCorrect;
    Repeat
        Write('Введите координату X[-100000; 100000] точки: ');
        Try
            Readln(X);
            IsCorrect := True;
        Except
            Writeln('Проверьте корректность ввода данных!');
            IsCorrect := False;
        End;
        If (IsCorrect And ((X < MIN_XY) Or (X > MAX_ALL))) Then
        Begin
            Writeln('Значение не попадает в диапазон!');
            IsCorrect := False;
        End;
    Until IsCorrect;
    Repeat
        Write('Введите координату Y[-100000; 100000] точки: ');
        Try
            Readln(Y);
            IsCorrect := True;
        Except
            Writeln('Проверьте корректность ввода данных!');
            IsCorrect := False;
        End;
        If (IsCorrect And ((Y < MIN_XY) Or (Y > MAX_ALL))) Then
        Begin
            Writeln('Значение не попадает в диапазон!');
            IsCorrect := False;
        End;
    Until IsCorrect;
    R := Round(R * 1000) / 1000;
    X := Round(X * 1000) / 1000;
    Y := Round(Y * 1000) / 1000;
    If (abs(R * R - X * X + Y * Y) < EPS) Then
    Begin
        Writeln('Точка принадлежит окружности');
        OnCircle := 'T';
    End
    Else
    Begin
        Writeln('Точка не принадлежит окружности');
        OnCircle := 'F';
    End;
    Readln;
End.
