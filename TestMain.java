package StudyRoom;

import java.io.*;
import java.util.*;

// 학번 비밀번호 이름 
// 스터디룸 예약 현황판 -> 배열, 학번 자체를 배열에 넣고 출력할 때 학번이 들어있으면 O로 표현

// 1. 회원가입, 로그인 클래스로 구현 
// 학생 클래스 : Student(학생 클래스) { string uid, string pwd, string name }
// 회원 정보 클래스 : MemberInfo
// 1_1_1 : field -> Student 배열 선언 , 생성자 ( 학번 파일을 불러옴 )
// 1_2_1 : method1 -> 회원가입  , method2 -> 등록된 학번인지 확인
// 1_2_2 : method3 -> 로그인 메서드 구현 -> true면 '예약 기능 클래스', false면 재 입력
// 1_3_1 : file ->  '학번'_'이름'_'비번'

// 2. 예약 기능 클래스 
// 스터디룸 예약 정보 관련 클래스 : Room { int 호실 번호, int 인원, int 예약 시간(번호), int 학번 }
// 스터디룸 예약 시스템 관련 클래스:Reservation
// 2_1 : field -> 생성자 ( 현황판 파일을 불러옴 ), Room 배열 선언
// 2_2 : method0 -> 현황판 출력 , method1 -> { 예약 하기(방 호실, 인원, 시간 입력) + 로그인 할 때 학번 입력한 걸로 학번 정보도 사용 }-> 가능여부 판단 , method2 -> 예약 취소, method3 -> 예약 조회, method4 -> 로그아웃
// 2_3 : file -> 현황판을 받아와서 새로 생성한 Room 배열에 정보 저장

// 3. 현황판 출력 메서드
// 그냥 예약 기능 클래스에 접근해서 현황판 출력 메서드 실행(method0)

// 4. 그냥 종료

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 맨 처음에 실행하면서 MemberInfo 객체 생성
		MemberInfo m = new MemberInfo();
		Scanner scanner = new Scanner(System.in);
		// 맨 처음 메뉴에서 현황판 출력을 해야하므로
		while (true) {
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 현황판");
			System.out.println("4. 종료");
			System.out.print(">> ");
			try {
				int choice = Integer.parseInt(scanner.nextLine().trim());
				// 문자열 입력 -> trim으로 공백 제거 -> 정수형으로 변환해서 choice에 담음
				// 입력받을 때 '__01' -> '1'
				switch (choice) {
				case 1:
					System.out.println("회원가입창으로 이동합니다.");
					// m 객체 -> 회원가입 메서드로 접근
					m.memberInsert();
					break;
				case 2:
					System.out.println("로그인창으로 이동합니다.");
					// m 객체 -> 로그인 메서드로 접근
					// a = m.login();
					// 로그인 return true -> 예약 기능 클래스로 접근
					m.loginMethod();
					break;
				case 3:
					System.out.println("현황판을 출력합니다.");
					String filename = "src/StudyRoom/ReservationInfo.txt";
					String[][] room_Info = new String[8][8];
					try (FileReader fileReader = new FileReader(filename);
							BufferedReader bufferedReader = new BufferedReader(fileReader)) {
						String line;
						int i=0;
						while ((line = bufferedReader.readLine()) != null) {
							//개행 문자별로 구분하도록
							// 선언한 배열 안에 split으로 구분하여 하나씩 넣기
							String[] tokens = line.split(" ");
							for(int j=0;j<8;j++) { 
								room_Info[i][j]=tokens[j];
							}
							i++;
						}
						fileReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("-------------------------------------------");
					System.out.print(" 호실 한도인원  1교시(10~12시)  2교시(12~14시)  3교시(14~16시)  4교시(16~18시)  5교시(18~20시)  6교시(20~22시)\n");
					for(int i=0;i<8;i++) {	
						for(int j=0;j<8;j++) {
							if(j>=2) {
								if(room_Info[i][j].equals("0")) {
									System.out.print("X             ");
								}
								else {
									System.out.print("O             ");
								}
							}
							else if(j==0) {
								System.out.print(room_Info[i][j]+"호실   ");
							}
							else if(j==1) {
								System.out.print(room_Info[i][j]+"        ");
							}
						} 
						System.out.println();
					}
					System.out.println("O: 예약 되어 있음 X: 비어있음 예약 가능");
					System.out.println("-------------------------------------------");
					break;
				case 4:
					String temp;
					while(true) {
					System.out.print("종료하시겠습니까?(Y/N)\n>> ");
					String choose = scanner.nextLine();
					temp = choose;
					if(choose.equals("Y") || choose.equals("N")) {
						break;
					}
					else {
						System.out.println("다시 입력해주세요.");
						continue;
					}
					}
					if(temp.equals("Y")) {
						System.out.println("프로그램을 종료합니다.");
						return;
					}
					else {
						System.out.println("메뉴로 돌아갑니다.");
						break;
					}
				default:
					System.out.println("1~4사이 숫자를 입력하세요!");
				}
			} catch (NumberFormatException E) {
				System.out.println("올바른 형식으로 입력하세요!");
			}
		}
	}
	
}

