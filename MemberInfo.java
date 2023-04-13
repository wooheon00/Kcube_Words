package StudyRoom;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

class Student // 한명의 회원정보를 지정
{
   private String uid, pwd, name;

   public Student(String uid, String name, String pwd) {
      this.uid = uid; // 학번
      this.name = name; // 이름
      this.pwd = pwd;
   }

   public String getUid() {
      return uid;
   }

   public String getPwd() {
      return pwd;
   }

   public void setPwd(String pwd) {
      this.pwd = pwd;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}

public class MemberInfo {
   private ArrayList<Student> members = new ArrayList<Student>();

   private int idx;

   public MemberInfo() {
      // 생성자에서 기존 학번 파일 불러와서 배열에 저장
      // 파일에는 {학번, 이름, 비번} \n {학번, 이름, 비번} ... 반복된 txt
      String filename = "src/StudyRoom/StudentInfoFile.txt";
      try (FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            //개행 문자별로 구분하도록
            String[] tokens = line.split("\t");
            // tokens[0]엔 학번, tokens[1]엔 이름, tokens[2]엔 비밀번호
            Student student1 = new Student(tokens[0], tokens[1], tokens[2]);
            // members 객체 배열에 생성한 student1 넣기
            members.add(student1);
         }
         fileReader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void memberInsert() { // 회원가입용 메소드
      String uid, pwd, name;
      Scanner sc = new Scanner(System.in);
      System.out.print("초기 화면으로 가려면 'q'를 누르세요.\n");
      while(true) {
         System.out.print("학 번 : ");
         uid = sc.nextLine();
         if(uid.equals("q")){
            return;
         }
         try {
            Integer.parseInt(uid);
         }catch(Exception E) {
            System.out.println("틀린 형식입니다. 다시 입력하세요!");
            continue;
         }
         // 조건을 if 문 안에 다 때려박고 잘못된 형식입니다. -> break
         if(uid.length()!=9||uid.charAt(0)!='2'||uid.charAt(1)!='0'
               ||(uid.charAt(2)!='1'&&uid.charAt(2)!='2')||
               ((uid.charAt(2)=='2'&&uid.charAt(3)>'3'))) {
            System.out.println("틀린 형식입니다. 다시 입력하세요!");
            continue;
         }
         if(!isUniqueID(uid)) {
            System.out.println("이미 사용중인 학번입니다.");
            continue;
         }
         break;
      }
      // 이름 예외처리
      while(true) {
            System.out.print("이 름 : ");
            name = sc.nextLine();
            int check = 0;
            if(name.contentEquals("q")) {
               return;
            }
            if (!name.matches("^[가-힣]+$")) {
               // 영어, 공백, 한글 형식
               if (name.matches(".*[a-zA-Z]+.*")) {
                   System.out.println("영어를 포함한 이름은 입력할 수 없습니다.");
                   continue;
               }
               else if(name.contains(" ")||name.contains("\t")) {
                  System.out.println("공백은 입력할 수 없습니다.");
                  continue;
               }
               else if(name.matches(".*\\d+.*")){
                  System.out.println("숫자는 입력할 수 없습니다.");
                  continue;
               }
               else{
                  System.out.println("올바른 형식을 입력하세요!");
                  continue;
               }
            }
            
            /*
             * for(int i=0;i<name.length();i++) {
             * if(isJaeum(name.charAt(i))||isMoeum(name.charAt(i))) { check=1;
             * System.out.println("형식에 맞게 입력해주세요."); break; } }
             */
            if(name.length()<2||name.length()>10) {
               System.out.println("길이에 맞게 이름을 입력하세요!");
               continue;
            }
            if(check==0) {
               break;
            }
         }
      //}
      // pwd 예외처리
      while(true) {
         System.out.print("암 호 : ");
         pwd = sc.nextLine();
         if(pwd.equals("q")){
            return;
         }
         if (pwd.length()<8 || pwd.length()>12) {
            System.out.println(">> 8~12자 영문, 숫자를 사용하세요.");
            continue;
         }
         else if (pwd.contains(" ")) {
               System.out.println(">> 공백은 포함될 수 없습니다.");
               continue;
         }
         else{  //영문 숫자 말고 다른 것 입력하면 다시 입력
            boolean correct= false;
               for(int i=0; i<pwd.length(); i++) {
               int index = pwd.charAt(i);
               if(!((index >=48 && index <= 57)||(index >=65 && index <=122))) {
                  System.out.println(">> 8~12자 영문, 숫자를 사용하세요. ");
                  correct =true;
                  break;
               }
            }if(correct)
               continue;
         }
         System.out.print("암호를 재설정하려면 'r'을 누르세요.\n암호확인 : ");
         boolean pass = false;
         while(true) {
            String pwd1 = sc.nextLine();
            if(pwd1.equals(pwd)) {
               pass = true;
               break;
            }
            else if (pwd1.equals("r")) {
               break;
            }
            else {
            System.out.println("암호가 일치하지 않습니다.");
            System.out.print("암호확인 : ");
            continue;
            }
         }
         if(pass)
            break;
         else
            continue;
      }
      
      Student newstudent = new Student(uid, name, pwd);
      members.add(newstudent);
      System.out.println("<< 학번 : "+uid+"  이름 : "+name+"    비밀번호 : "+pwd+" 로 가입을 진행합니다. >>");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudyRoom/StudentInfoFile.txt", false));
            for (Student student : members) {
               //System.out.println(student.getUid() + "\t" + student.getPwd() + "\t" + student.getName());
                writer.write(student.getUid() + "\t" + student.getName() + "\t" + student.getPwd() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
      System.out.println("가입 완료!! \n");
   }
   
   private boolean isJaeum(char c) {
         return Pattern.matches("^[ㄱ-ㅎ]+$", String.valueOf(c));
      }

   private boolean isMoeum(char c) { 
         return Pattern.matches("^[ㅏ-ㅣ]+$", String.valueOf(c)); 
         }
   
   private boolean isUniqueID(String uid) {
      for(Student st1 : members) {
         if(st1.getUid().equals(uid)) {
            return false;
         }
      }
      return true;
   }
   // 로그인 메소드 구현
   public void loginMethod() {
      Scanner scanner = new Scanner(System.in);
      String inputid;
      String inputpwd;
      boolean judge = false;   //로그인 성공인지 아닌지
      System.out.println("초기화면으로 가려면 'q'를 누르세요.");
      while(true) {
         // 수정 꼭 하기
         System.out.print("학번 : ");
         inputid = scanner.nextLine();
         if(inputid.equals("q")) {
            return;
         }
         try {
            Integer.parseInt(inputid);
         }catch(Exception E) {
            System.out.println("틀린 형식입니다. 다시 입력하세요!");
            continue;
         }
         // 조건을 if 문안에 다 때려박고 잘못된 형식입니다. -> break;
         if(inputid.length()!=9||inputid.charAt(0)!='2'||inputid.charAt(1)!='0'
               ||(inputid.charAt(2)!='1'&&inputid.charAt(2)!='2')||
               ((inputid.charAt(2)=='2'&&inputid.charAt(3)>'3'))) {
            System.out.println("틀린 형식입니다. 다시 입력하세요!");
            continue;
         } 
         while (true) {
            //////비밀번호 입력 ///////
            System.out.print("암호 : ");
            inputpwd = scanner.nextLine();
            if(inputpwd.equals("q")) {
               return;
            }
            if (inputpwd.length()<8 || inputpwd.length()>12) {
               System.out.println(">> 8~12자 영문, 숫자를 사용하세요.");
               continue;
            }
            else if (inputpwd.contains(" ")) {
                  System.out.println(">> 공백은 포함될 수 없습니다.");
                  continue;
            }
            else{  //영문 숫자 말고 다른 것 입력하면 다시 입력
               boolean correct= false;
                  for(int i=0; i<inputpwd.length(); i++) {
                  int index = inputpwd.charAt(i);
                  if(!((index >=48 && index <= 57)||(index >=65 && index <=122))) {
                     System.out.println(">> 8~12자 영문, 숫자를 사용하세요. ");
                     correct =true;
                     break;
                  }
               }if(correct)
                  continue;
            }
            
            if(!InfoCheck(inputid, inputpwd)) {
               System.out.println("학번 또는 암호가 일치하지 않습니다.");
               break;
            }else
               judge = true;
            break;
         }
         if(judge) {
            System.out.println("로그인 성공!");
            Reservation r = new Reservation(inputid);
            return;
            //break;
         }
         else continue;
      }
      // 학번 예외처리
   }
   private boolean InfoCheck(String id, String pwd) {
      for(Student s1 : members) {
         if(s1.getUid().equals(id)) 
            if(s1.getPwd().equals(pwd)) 
               return true;
      }
      return false;
   }
   

}