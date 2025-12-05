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
		String title;
		int input;
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		
		//起動時に読み込み（CSV読み込みメソッドの呼び出し）
		List<Todo> todos = loadTodos();
		
		//終了が選択されない限り繰り返し
		while(!exit) {
			//メニュー表示と選択
			System.out.println("【1】追加　【2】一覧表示　【3】変更　【4】終了");
			System.out.println("番号を選択してください＞＞");
			input = Integer.parseInt(scanner.nextLine());
			
			switch(input) {
			//【1】追加（ToDoを入力しリストに追加）
			case 1 -> {
				//タスク名の設定
				System.out.println("ToDoを入力してください。＞＞");
				title = scanner.nextLine();
				Todo todo = new Todo(title);
				//重要度の設定
				System.out.println("【1】高　【2】中　【3】低");
				System.out.println("優先度を選択してください＞＞");
				input = Integer.parseInt(scanner.nextLine());
				todo.setPriority(input);
				todos.add(todo);
				System.out.println("優先度を変更しました。");
			}
			//【2】一覧表示（全てのToDoを表示）
			case 2 -> {
				System.out.println("=====ToDo一覧=====");
				for (int i = 0; i < todos.size(); i++) {
					//todos.get(i)でi番目の要素のtodoインスタンスが呼ばれるが、
					//printlnの中に入っているので、さらにtoString()が呼ばれる
					System.out.println((i + 1) + "：" + todos.get(i));
				}
				System.out.println("以上です。");
				System.out.println();
			}
			//【3】タスク内容変更
			case 3 -> {
				System.out.println("変更するタスクの番号を入力してください＞＞");
				int num = Integer.parseInt(scanner.nextLine());
				
				//入力された番号が有効なら続行
				if (num >= 1 && num <= todos.size()) {
					//num=1の場合は0番目の要素を取得し、done=trueに変更
					Todo target = todos.get(num - 1);
					
					System.out.println(target);
					System.out.println("【1】済・未の切り替え　【2】タスク名の変更　【3】優先度の変更　【4】タスクの削除");
					System.out.println("何をしますか？＞＞");
					input = Integer.parseInt(scanner.nextLine());
					
					switch(input) {
					//済・未の切り替え
					case 1 -> {
						//done=true（済）の場合はfalse（未）にする
						if (target.getDone()) {
							target.setDone(false);
							System.out.println("ステータスを「未完了」に戻しました");
						
						//done=false（未）の場合はtrue（済）にする
						}else {
							target.setDone(true);
							System.out.println("ステータスを「完了済」にしました");
						}
					}
					//タスク名の変更
					case 2 -> {
						System.out.println("変更後のタスク名を入力してください>>");
						title = scanner.nextLine();
						if (!title.isEmpty()) {
							target.setTitle(title);
							System.out.println("タイトルを変更しました");
						}
					}
					//優先度の変更
					case 3 -> {
						System.out.println("【1】高　【2】中　【3】低");
						System.out.println("番号を選択してください＞＞");
						input = Integer.parseInt(scanner.nextLine());
						target.setPriority(input);
						System.out.println("優先度を変更しました。");
					}
					//タスクの削除
					case 4 -> {
						todos.remove(num -1);
						System.out.println("削除しました。");
					}
					}
				}else {
					System.out.println("番号が不正です");
				}
			}
			//【4】終了（アプリの終了）
			case 4 ->{
				//CSV保存メソッドを呼び出し、繰り返しから抜ける
				saveTodos(todos);
				System.out.println("終了します。");
				exit = true;
			}
			//入力された内容が上記以外の場合はやり直し
			default -> {
				System.out.println("無効な入力です");
			}
			}
		}
		scanner.close();
	}
	
	//CSV読み込みメソッド
	private static List<Todo> loadTodos(){
		List<Todo> list = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){
			String line = null;
			while((line = br.readLine()) != null) {
				//CSVをカンマで分割。
				//split(",",2)は、「文字列を,で区切って最大2要素の配列にする」という意味
				String[] parts = line.split(",", 3);

				//要素1が"1"ならtrue、"0"ならfalse
				//要素2をタイトルとして、Todoインスタンスを作成しlistに追加
				boolean done = parts[0].equals("1");
				String title = parts[1];
				String priority = parts[2];
				list.add(new Todo(title, done));
			}
			System.out.println("前回のToDoを読み込みました");
		}catch(IOException e) {
			//ファイルが見つからない場合は無視
			System.out.println("保存ファイルが見つからなかったため、新規作成します");
		}
		return list;
	}
	
	//CSV保存メソッド
	private static void saveTodos(List<Todo> todos) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
			for (Todo todo : todos) {
				//doneがtrueの場合は「1」、falseの場合は「0」とする
				String status = todo.getDone() ? "1" : "0";
				//1,洗濯物をする　のような形式で書き込み
				bw.write(status + "," + todo.getTitle() + "," + todo.getPriority());
				//改行文字を入れる
				bw.newLine();
			}
		}catch(IOException e) {
			System.out.println("保存に失敗しました：" + e.getMessage());
		}
	}
}
