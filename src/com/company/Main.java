package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException, MyException { //здесь пробрасываем исключения
//потомучто используется InputStream, и MyException поэтому онизаписываются после ключевого слова throws
        //по другому говоря, мы можем получить исключение и эти штуки пишутсся для возможности его обработки,
        //конечно я его не обрабатывал никак. обработка пишется в блоках try catch, чтобы прога не падала,
        //а справлялась с этим
        int firstOperand;//смотри, выражение a + b . а - первый операнд (firstOperand), b - второй (secondOperand)
        int secondOperand;
        String inputString; //переменная для хранения строки которую введет пользователь
        double result = 0; //результат операции берем тип double потому что может быть деление
        InputOutput io = new InputOutput(); //создаем объект класса InoutOutput вот можно было все эти классы и объекты
        // не создавать просто в методе main все написать и все. но в условии было прописано сделайте чтобы были классы
        //
        CheckRomeOrArabicNumber checkNumber = new CheckRomeOrArabicNumber(); //создаем объект класса CheckRomeOrArabicNumber
        CheckString chStr = new CheckString(); //создаем объект класса CheckString
        inputString = io.readString(); //получаем строку ввденую пользователем. а именно у нас есть объект io
        //созданный на 20 строке и мы вызываем метод объекта readString который возвращает строку
        chStr.checkString(inputString); //проверяем строку через объект проверки строк вызывая метод checkString
        //передавая ему введеную пользователем строку
        //то есть видишь? создан класс который отвечает за ввод строки
        //а другой класс отвечает за обработку строки
        //если бы это было промышленное приложение то все эти классы были каждый в своем файле
        //это типа правило хорошего тона в java. но тут просто тестовое задание поэтому все в одном месте
        checkNumber.pullOutOperands(chStr.getSince(), inputString);//объект для проверки чисел и вызываем его метод
        //который предназначен для вытягивания операндов (чисел из строки) в этот метод передаем знак который достается вот так
        //chStr.getSince(), и передаем строку
        if (checkNumber.compareOperands()) {//то же самое у объекта проверки чисел есть метод сравнения операндов
            // то есть он проверяет а число ли там вообще и этот метод возвращает либо false либо true
            firstOperand = checkNumber.getFirstNumber();//здесь мы получаем готовые числа и присваиваем их нашим операндам
            secondOperand = checkNumber.getSecondNumber();
            switch (chStr.getSince()) {//в зависимости от знака совершаем ту или иную операцию над оперендами
                case '+':                    result = firstOperand + secondOperand;                    break;
                case '-':                    result = firstOperand - secondOperand;                    break;
                case '*':                    result = firstOperand * secondOperand;                    break;
                case '/':
                    if (secondOperand == 0) {//сообщаем пользователю что на ноль делить вредно
                        System.out.println("в этой стране деление на ноль вне закона, за вами уже выехали!!!");
                    } else {
                        result = (double) firstOperand / (double) secondOperand;
                    }
                    break;
            }
        } else {
            throw new MyException("некорректный ввод"); //если в 40 строке результат не был true то попали в эту ветку
            //и пробрасываем это вот когда throw видишь, то они говорят пробросить исключение.
            //а по сути смотри создаем обект нашего класса MyException и передаем в конструктор сообщение которое выводится
            //когда прога падает
        }
        System.out.println(result);//тут выдаем результат
        RIM R = new RIM();
        int result2 = (int) result;
        R.main2(result2);
    }
}
class CheckRomeOrArabicNumber {
    //этот класс может некорректно называется потомучто делает он немного больше чем просто проверка арабских или римских чисел
    private int firstNumber;//поле класса для хранения первого полученного числа (операнда)
    private int secondNumber;//поле класса для хранения второго полученного числа (операнда)
    private String firstOperand;//мы приняли строку и поэтому первый операнд сначала достанется в строчном представлении
    private String secondOperand;//как и второй, поэтому под это дело мы заводим строчные переменные
    private int index;//индекс для того чтобы в нужном месте сделать срез. в java это называется извлечь подстроку
    private boolean fOperandIsArabic;//эти переменные для хранения результата то есть если true значит первый операнд арабское число
    private boolean sOperandIsArabic;//если тру значит второй операнд арабское число
    private boolean fOperandIsRome;//если тру значит первый операнд римское число
    private boolean sOperandIsRome;//если тру значит второй операнд римское число
    public void pullOutOperands(char since, String s) throws MyException{//метод вытягивания операндов из строки
//в метод передаем знак операции и саму введеную строку
        index = s.indexOf(since);//вычисляем позицию где там находится знак в строке
        firstOperand = s.substring(0, index);//вытаскиваем подстроку до знака
        secondOperand = s.substring(index + 1, s.length()); //вытаскиваем подстроку после знака
        if (firstOperand.length() == 0 || secondOperand.length() == 0) { //если длина хоть оодной подстроки == 0
            //это значит ввели чтото типа "+ 10" или "8 +"
            throw new MyException("один из операндов отсутствует");//поэтому если это так выбрасываем исключение
        }
    }

    public boolean isArabicNumber(String s) { //метод проверяет числа ли это
        for (char c : s.toCharArray()) {//цикл форич пробегает по символам
            if (!Character.isDigit(c)) { //если хоть один символ не удовлетворяет возвращаем фолс
                return false;
            }
        }
        return true;
    }
    public boolean isRomeNumber(String s) throws MyException { //метод проверяет римские цифры у нас или нет
        if (s.length() > 4) { //так как цифры римские и только до 10 то больше 4 символов быть не может
            throw new MyException("неправильный операнд");
        }
        s = s.trim(); //у переданной подстроки убираем пробелы по бокам иначе никогда не найдет он там римских цифр
        for (char c : s.toCharArray()) {
            if (c == 'I' || c == 'X' || c == 'V'){
                return true;
            }
        }
        return false;
    }

    public int fromRomeToArabic(String operand){
        int number = 0;
        switch (operand) {
            case "I":       number = 1;                break;
            case "II":      number = 2;                break;
            case "III":     number = 3;                break;
            case "IV":      number = 4;                break;
            case "V":       number = 5;                break;
            case "VI":      number = 6;                break;
            case "VII":     number = 7;                break;
            case "VIII":    number = 8;                break;
            case "IX":      number = 9;                break;
            case "X":       number = 10;               break;
            default: System.out.println("Мы работаем только с числами не более 10");
            number = 0;
            break;
        }
        return number;
    }

    public boolean compareOperands() throws MyException {
//этот метод смотрит чтобы сразу два числа слева и справа были одного вида либо все арабские либо все римские
        firstOperand = firstOperand.trim();
        secondOperand = secondOperand.trim();
        fOperandIsArabic = isArabicNumber(firstOperand);
        sOperandIsArabic = isArabicNumber(secondOperand);
        if (fOperandIsArabic && sOperandIsArabic) {
            //если все арабские то переводим все в числа
            firstNumber = Integer.parseInt(firstOperand);
            secondNumber = Integer.parseInt(secondOperand);
            return true;
        } else {
            //иначе проверяем может они римские
            fOperandIsRome = isRomeNumber(firstOperand);
            sOperandIsRome = isRomeNumber(secondOperand);
            if (fOperandIsRome && sOperandIsRome) {
                //если оба римские то вызываем метод перевода римских цифр в арабские
                firstNumber = fromRomeToArabic(firstOperand);
                secondNumber = fromRomeToArabic(secondOperand);
                return true;
            } else {
                return false;
            }
        }
    }

    public int getFirstNumber(){
        return firstNumber; //возвращаем готовое число если оно есть
    }

    public int getSecondNumber(){
        return secondNumber;//если второе число есть то тоже возвращаем
    }

}

class CheckString {

    private int sinceCount;//переменная для подсчета знаков +, /, *, -
    private char since;//переменная для хранения самого знака операции

    public char getSince(){//получить знак операции
        return since;

    }

    public void checkString (String s) throws MyException {
//проверка на дурака путую строку не пропускаем
        if (s.equals("")) {

            throw new MyException("строка путсая");

        } else {

            countSince(s);
        }

    }

    public void countSince(String s) throws MyException{
        //этот метод для проверки строки на много знаковость если их до фига, то отправляем пользователю исключение
        sinceCount = 0;
        char c;

        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);

            if (c == '-' || c == '+' || c == '/' || c == '*') {

                since = c;
                sinceCount++;

            }
        }

        if (sinceCount == 0) {
//а если знаков нет а числа есть то тоже посылаем исключение
            throw new MyException("отсутствуют знаки, считать нечего");

        }

        if (sinceCount > 1) {

            throw new MyException("Вы ввели выражение с более чем одним знаком");

        }

    }

}
class InputOutput {
    //этот класс занимается тем что выводит сообщение: Введите сообщение
    //и потом ожидает ввода от пользователя
    private String inputString;

    InputOutput(){

        inputString = "";
    }

    public String readString () throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите выражение для вычисления: ");
        inputString = reader.readLine();

        return inputString;
    }

    public void printString (String s) {

        System.out.println(s);

    }

}
class MyException extends Exception {
    //а это класс создающий пользовательское исключение. наследуемся от класса Exception и вызываем конструктор родительского класса
    //передав ему сообщение которое хотим видеть
    MyException(String msg) {
        super(msg);

    }
}

class RIM {
    public static void main2(int args) {
        String result = "";

        int y = args; //входная цифра результата
        y= (int) args; //округлили
        if (y>3999 ) {    System.out.println("Числа больше 3999  доступны в платной версии :) "); }
        String Dec = Integer.toString(y); //преобразуем число в строку
        //System.out.println("Округляем число,получили  = "+Dec );
        System.out.println("---------------------------------------------");
        RimDelim(Dec);

    }
    public static  char[] RimDelim (String x){ //выводим все цифры поочередно
        char[] line = x.toCharArray(); // преобразуем строку в массив символов
        String result="";

        switch (x.length()){
            case 1:
                int x1 = Character.getNumericValue(line[0]);
                RimEdenica(x1);
                System.out.println("римская цифра = "+RimEdenica(x1));
                break;
            case 2: int x2 = Character.getNumericValue(line[1]);//переводим символо в число тип
                int x21 = Character.getNumericValue(line[0]);
                RimDec(x21);
                RimEdenica(x2);
                System.out.println("римская цифра = "+RimDec(x21)+RimEdenica(x2));  break;
            case 3:
                int x31 = Character.getNumericValue(line[0]);
                int x32 = Character.getNumericValue(line[1]);
                int x33 = Character.getNumericValue(line[2]);
                RimEdenica(x33);RimDec(x32);RimSot(x31);
                System.out.println("римская цифра = "+RimSot(x31)+RimDec(x32)+RimEdenica(x33));
                break;
            case 4:  int x41 = Character.getNumericValue(line[0]);
                int x42 = Character.getNumericValue(line[1]);
                int x43 = Character.getNumericValue(line[2]);
                int x44 = Character.getNumericValue(line[2]);
                RimTis(x41);RimSot(x42);RimDec(x43);RimEdenica(x44);
                System.out.println("римская цифра = "+RimTis(x41)+RimSot(x42)+RimDec(x43)+RimEdenica(x44));break;
            default:
                System.out.println("Числа больше 4 разрядом доступны в платной версии :)");
        }
        return line;
    }
    public static String RimEdenica(int x) {// из арабской -> римскую
        String rim = "";
        switch (x) {
            case 9:                rim = "IX";break;
            case 8:                rim = "VIII";break;
            case 7:                rim = "VII";break;
            case 6:                rim = "VI";break;
            case 5:                rim = "V";break;
            case 4:                rim = "IV";break;
            case 3:                rim = "III";break;
            case 2:                rim = "II";break;
            case 1:                rim = "I";break;
            default:
                rim="";break;
        }
        return rim;
    }  //единицы арабские в римские переводим
    public static String RimDec(int x) {         //десятки арабские в римские переводим
        String rim = "";
        switch (x) {
            case 9:                rim = "XC";break;
            case 8:                rim = "LXXX";break;
            case 7:                rim = "LXX";break;
            case 6:                rim = "LX";break;
            case 5:                rim = "L";break;
            case 4:                rim = "XL";break;
            case 3:                rim = "XXX";break;
            case 2:                rim = "XX";break;
            case 1:                rim = "X";break;
        }
        return rim;
    }
    public static String RimSot(int x) {// из арабской -> римскую
        String rim = "";
        switch (x) {
            case 9:                rim = "CM";      break;
            case 8:                rim = "DCCC";    break;
            case 7:                rim = "DCC";     break;
            case 6:                rim = "DC";      break;
            case 5:                rim = "D";       break;
            case 4:                rim = "CD";      break;
            case 3:                rim = "CCC";     break;
            case 2:                rim = "CC";      break;
            case 1:                rim = "C";       break;
        }
        return rim;
    }
    public static String RimTis(int x) {// из арабской -> римскую
        String rim = "";
        switch (x) {
            case 3:      rim = "MMM";break;
            case 2:      rim = "MM";break;
            case 1:      rim = "M";break;
        }
        return rim;
    }
    public static void CheckNUM(int x){
        if (x >10) {
            System.out.println("Числа больше 10 принимаются в платной версии :)");
                   }

    }

}






