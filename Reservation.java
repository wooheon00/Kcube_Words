package StudyRoom;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;



public class Reservation {
	// 배열 선언
	String[][] room_Info = new String[8][8];
	// field .. 남은 것들 선언
	String user_id; // 로그인한 학번을 받아오는 user_id 필드
	Scanner sc = new Scanner(System.in);
	public Reservation(String uid) {
		this.user_id = uid;
		String filename = "src/StudyRoom/ReservationInfo.txt";
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
		Reservation_Menu();
	}
	
	public void Reservation_Menu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1) 예약 신청 2) 예약 취소");
			System.out.println("3) 예약 조회 4) 로그아웃");
			System.out.print(">> ");
			try {
				int choice = Integer.parseInt(sc.nextLine().trim());
				// 문자열 입력 -> trim으로 공백 제거 -> 정수형으로 변환해서 choice에 담음
				// 입력받을 때 '__01' -> '1'
				switch (choice) {
				case 1:
					System.out.println("예약 신청으로 이동합니다.");
					// 예약 신청으로 접근
					Reservation_insert();
					break;
				case 2:
					System.out.println("예약 취소로 이동합니다.");
					// 예약 취소로 접근
					Reservation_delete();
					break;
				case 3:
					System.out.println("예약 조회로 이동합니다.");
					// 예약 조회로 접근
					Reservation_check();
					break;
				case 4:
					System.out.println("메뉴로 돌아갑니다.");
					// 로그아웃해서 메뉴로 돌아가게
					return;
				default:
					System.out.println("1~4사이 숫자를 입력하세요!");
				}
			} catch (NumberFormatException E) {
				System.out.println("올바른 형식으로 입력하세요!");
			}
		}
	}
	public void Recent() {
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
	}
	public void Reservation_insert() {
		Recent();
		// 배열 보여주기 -> 학번은 O로 바꿔서 , X는 그대로!
		String choose = "O";
		int choose1=0;
		int time=0;
		int people=0;
		while(true) {
		while(true) {
			System.out.print("호실을 선택해주세요(메뉴창 이동: q)\n>> ");
			try {
				choose = sc.nextLine().trim();
				choose1 = Integer.parseInt(choose);
			if(choose1<1 || choose1>8) {
				System.out.println("1~8사이 숫자를 입력하세요!");
			}
			} catch (NumberFormatException E) {
				if(choose.equals("q")) {
					System.out.println("예약 신청을 종료하고 메뉴로 돌아갑니다.");
					return;
				}
				System.out.println("올바른 형식으로 입력하세요!");
			}
			if(choose1>=1&&choose1<=8) {
				choose1--;
				break;
			}
		}
		while(true) {
			try {
				System.out.print("교시를 입력해주세요(메뉴창 이동: q)\n>> ");
				choose = sc.nextLine().trim();
				time = Integer.parseInt(choose);
			if(time<1 || time>6) {
				System.out.println("1~6사이 숫자를 입력하세요!");
			}
			} catch (NumberFormatException E) {
				if(choose.equals("q")) {
					System.out.println("예약 신청을 종료하고 메뉴로 돌아갑니다.");
					return;
				}
				System.out.println("올바른 형식으로 입력하세요!");
			}
			if(time>=1&&time<=6) {
				time++;
				break;
			}
		}
		while(true) {
			try {
				System.out.print("인원을 입력해주세요(메뉴창 이동: q)\n>> ");
				choose = sc.nextLine().trim();
				people = Integer.parseInt(choose);
				if(people <= 0) {
					System.out.println("올바른 인원 수를 입력하세요!");
				}
				else {
					break;
				}
			} catch (NumberFormatException E) {
				if(choose.equals("q")) {
					System.out.println("예약 신청을 종료하고 메뉴로 돌아갑니다.");
					return;
				}
				System.out.println("올바른 형식으로 입력하세요!");
			}
		}
		for(int i=0;i<8;i++) {
			for(int j=2;j<8;j++) {
				if(room_Info[i][j].equals(this.user_id)) {
					System.out.println("금일 이미 "+ (i+1) +"호실 " + (j-1) + "교시 예약되어 있습니다.");
					return;
				}
			}
		}
		if(!room_Info[choose1][time].equals("0")) {
			System.out.println("해당 호실은 이미 차있습니다.");
			break;
		}
		if(people > Integer.parseInt(room_Info[choose1][1])) {
			System.out.println("해당 호실 인원 초과입니다.");
			break;
		}
		else {
			if(Integer.parseInt(room_Info[choose1][1])-people > 2) {
				System.out.println("해당 호실에 입실하시기에 인원이 부족합니다.");
				break;
			}
		}
		while(true) {
		System.out.print("이 호실로 예약하시겠습니까?(Y/N)\n>> ");
		choose = sc.nextLine();
		if(choose.equals("Y")) {
			System.out.println("k-cube " + (choose1 + 1) + "호실(" +
		people + "명) " + (time - 1) + "교시 예약이 완료되었습니다.");
			room_Info[choose1][time] = user_id;
			file_change("src/StudyRoom/ReservationInfo.txt");
			return;
		}
		else if(choose.equals("N")) {
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		else {
			System.out.println("다시 입력해주세요.");
		}
		}
		}
	}
	public void Reservation_delete() {
		String choose = "O";
		for(int i=0;i<8;i++) {
			for(int j=2;j<8;j++) {
				if(room_Info[i][j].equals(this.user_id)) {
					 System.out.println("k-cube "+(i+1) + "호실 " + (j-1) + "교시 예약되어 있습니다.");
					while(true) {
					System.out.print("이 예약을 취소하시겠습니까?(Y/N)\n>> ");
					choose = sc.nextLine();
					if(choose.equals("Y")) {
						room_Info[i][j] = "0";
						file_change("src/StudyRoom/ReservationInfo.txt");
						System.out.println("취소 되었습니다.");
						return;
					}
					else if(choose.equals("N")) {
						System.out.println("메뉴로 돌아갑니다.");
						return;
					}
					else {
						System.out.println("다시 입력해주세요.");
					}
					}
				}
			}
		}
		System.out.println("금일 예약 내역이 없습니다.");
		// 배열 돌면서 id랑 겹치는데 찾고
		// 질문하면서 진짜 지우냐고 묻고
		// 찾으면 X로 바꾸기
		// 없으면 없다고 출력
	}
	public void Reservation_check() {
	    System.out.println("당신의 예약 내역을 조회합니다.");
	    for(int i=0; i<8; i++) {
	        for(int j=2; j<8; j++) {
	            if(room_Info[i][j].equals(user_id)) {
	                System.out.println("k-cube "+(i+1) + "호실 " + (j-1) + "교시 예약되어 있습니다.");
	                return;
	            }
	        }
	    }
	    System.out.println("금일 예약 내역이 없습니다.");
	}
	public void file_change(String filename) {
		try (FileOutputStream fos = new FileOutputStream(filename, false)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
		try {
			PrintWriter writer = new PrintWriter("src/StudyRoom/Reservationinfo.txt");
			for(int i=0;i<8;i++) {
				for(int j=0;j<8;j++) {
					writer.write(room_Info[i][j]+" ");
				}
				if(i!=7)
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
