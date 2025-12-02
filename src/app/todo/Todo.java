package app.todo;

public class Todo {
	private String title; //ToDoのタイトル
	private boolean done; //完了済みかどうか
	
	//コンストラクタ
	public Todo(String title, boolean done) {
		this.title = title;
		this.done = done;
	}
	public Todo(String title) {
		this(title, false);
	}
	
	//getter
	public String getTitle() {
		return this.title;
	}
	public boolean getDone() {
		return this.done;
	}
	
	//setter
	public void setDone(boolean done) {
		this.done = done;
	}
	
	@Override
	//toStringメソッド。println()の中にTodoクラスの変数を入れるだけで情報を表示してくれる
	public String toString() {
		//三項演算子（条件 ? 真のときの値 : 偽のときの値）
		//を使い、done=trueのときは[済]を、falseのときは[未]をタイトルの前につける
		return (this.done ? "[済]" : "[未]") + " " + this.title;
	}
}
