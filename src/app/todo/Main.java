package app.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<String> todos = new ArrayList<>();
		
		while(true) {
			//メニュー表示と選択
			System.out.println("【1】追加　【2】一覧表示　【3】完了（削除）【4】終了");
			System.out.println("選択してください");
			int input = Integer.parseInt(scanner.nextLine());
			
			switch(input) {
			//【1】追加（ToDoを入力しリストに追加）
			case 1 -> {
				System.out.println("ToDoを入力してください。");
				String todo = scanner.nextLine();
				todos.add(todo);
				System.out.println("追加しました。");
			}
			//【2】一覧表示（全てのToDoを表示）
			case 2 -> {
				System.out.println("=====ToDo一覧=====");
				for (int i = 0; i < todos.size(); i++) {
					System.out.println((i + 1) + "：" + todos.get(i));
				}
				System.out.println("以上です。");
				System.out.println();
			}
			//【3】完了（削除）（完了したToDoをリストから削除）
			case 3 -> {
				System.out.println("完了したToDo番号を入力してください");
				int num = Integer.parseInt(scanner.nextLine());
				
				if (num >= 1 && num <= todos.size()) {
					String removed = todos.remove(num -1);
					System.out.println("「" + removed + "」を完了しました");
				}else {
					System.out.println("番号が不正です");
				}
			}
			//【4】終了（アプリの終了）
			case 4 ->{
				System.out.println("終了します。");
				scanner.close();
				break;
			}
			//入力された内容が上記以外の場合はやり直し
			default -> {
				System.out.println("無効な入力です");
			}
			}
		}
		
	}
}
