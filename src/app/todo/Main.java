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
		//終了判定
		boolean exit = false;
		
		//起動時に読み込み（CSV読み込みメソッドの呼び出し）
		List<Todo> todos = loadTodos();
		
		//終了が選択されない限り繰り返し
		while(!exit) {
			//メニュー表示と選択
			System.out.println("【1】追加　【2】一覧表示　【3】完了（削除）【4】終了");
			System.out.println("選択してください");
			int input = Integer.parseInt(scanner.nextLine());
			
			switch(input) {
			//【1】追加（ToDoを入力しリストに追加）
			case 1 -> {
				System.out.println("ToDoを入力してください。");
				
				String title = scanner.nextLine();
				Todo todo = new Todo(title);
				todos.add(todo);
				System.out.println("追加しました。");
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
			//【3】完了（完了したToDoを「済」に変更）
			case 3 -> {
				System.out.println("完了したToDo番号を入力してください");
				int num = Integer.parseInt(scanner.nextLine());
				
				//入力された番号が有効なら削除
				if (num >= 1 && num <= todos.size()) {
					//num=1の場合は0番目の要素を取得し、done=trueに変更
					Todo target = todos.get(num - 1);
					target.setDone(true);
					System.out.println("「" + target.getTitle() + "」を完了しました");
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
				String[] parts = line.split(",", 2);

				//要素1が"1"ならtrue、"0"ならfalse
				//要素2をタイトルとして、Todoインスタンスを作成しlistに追加
				boolean done = parts[0].equals("1");
				String title = parts[1];
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
				bw.write(status + "," + todo.getTitle());
				//改行文字を入れる
				bw.newLine();
			}
		}catch(IOException e) {
			System.out.println("保存に失敗しました：" + e.getMessage());
		}
	}
}
