package project.monsterBox.servlet;

/**
 *
 * HTTPリクエストで受け取ったデータを無害な文字列として変換する機能を有したクラス
 *
 * @author morikawahiroki
 *
 */
public class HtmlSanitizingUtil {
	/**
	 * htmlタグのエスケープ処置をおこなう．
	 * @param input
	 * @return
	 */
	public static String sanitize(String input) {

		StringBuilder sb = new StringBuilder();
		for(char c : input.toCharArray()) {
			switch(c) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			case '\'':
				sb.append("&#39;");
				break;
			case ',':
				sb.append("@");
			default :
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
}
