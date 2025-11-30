package app.todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	//保存先CSVファイルの名前を設定
	private static final String FILE_NAME = "todo.csv";
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		//起動時に読み込み（CSV読み込みメソッドの呼び出し）
		List<String> todos = loadTodos();
		
		//【4】終了が選択されない限り繰り返し
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
				
				//入力された番号が有効なら削除
				if (num >= 1 && num <= todos.size()) {
					String removed = todos.remove(num -1);
					System.out.println("「" + removed + "」を完了しました");
				}else {
					System.out.println("番号が不正です");
				}
			}
			//【4】終了（アプリの終了）
			case 4 ->{
				//CSV保存メソッドを呼び出し、繰り返しから抜ける
				saveTodos(todos);
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
	
	//CSV読み込みメソッド
	private static List<String> loadTodos(){
		List<String> list = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
			String line = null;
			while((line = br.readLine()) != null) {
				list.add(line);
			}
			System.out.println("前回のToDoを読み込みました");
		}catch(IOException e) {
			//ファイルが見つからない場合は無視
			System.out.println("保存ファイルが見つからなかったため、新規作成します");
		}
		return list;
	}
	
	//CSV保存メソッド
	private static void saveTodos(List<String> todos) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
			for (String todo : todos) {
				bw.write(todo);
				//改行文字を入れる
				bw.newLine();
			}
		}catch(IOException e) {
			System.out.println("保存に失敗しました：" + e.getMessage());
		}
	}
}
