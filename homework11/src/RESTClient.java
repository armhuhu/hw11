import static us.monoid.web.Resty.*;

import java.util.Scanner;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.Content;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import us.monoid.web.Resty.*;

public class RESTClient {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		Resty r = new Resty();
		String url = "http://frozen-ravine-4990.herokuapp.com/microposts";
		try{
			JSONResource obj =r.json(url);
			//System.out.println(obj.get("1"));
			JSONArray ary = obj.array();
			for(int i=0; i<ary.length();i++){
				System.out.println(ary.get(i));
			}
			
			System.out.println("******************INSTRUCTION**********************\n" +
					"commands:\n" +
					"\tindex\n" +
					"\tshow [id]\n" +
					"\tcreate [ower_name] [text_message]\n" +
					"\tupdate [id] [text_message]\n" +
					"\tdelete [id]\n" +
					"* There is no input verification, so don't mistype!\n" +
					"*********************************************************");
			while(true){

				System.out.println("Type a command(index,show,create,update,delete):");
				String option1 ="";
				String option2 = "";
				String command = sc.next();
				//receiving orders
				switch(command){
				case "index":
					break;
				case "show":
					option1 = sc.next().trim();
					break;
				case "create":
					option1 = sc.next().trim();//author
					option2 = sc.nextLine().trim();//message
					break;
				case "update":
					option1 = sc.next().trim(); //post to update
					option2 = sc.nextLine().trim(); //update message
					break;
				case "delete":
					option1=sc.next().trim(); //post to delete
					break;
				}
				
				//command execution
				switch(command){
				case "index":
					for(int i=0; i<ary.length();i++){
						JSONObject o = (JSONObject) ary.get(i);
						System.out.print(o.get("id")+",");
					}
					System.out.println();
					
					break;
				case "show":
					String id =option1;
					
					System.out.println("You choose micropost id="+id);
					for(int i=0; i<ary.length();i++){
						JSONObject o = (JSONObject) ary.get(i);
						if(o.getString("id").equals(id)){
							System.out.println("ower:\t"+o.get("ower_name"));
							System.out.print("text_message:\t");
							System.out.println(o.get("text_message"));
						}
					}
					break;
				case "create":
					String post = "http://frozen-ravine-4990.herokuapp.com/microposts.json";
					JSONResource um = r.json(post,form(data("micropost[text_message]",option2),
							data("micropost[ower_name]", option1)));
					System.out.println(um);
					System.out.println("finish posting");
					break;
				case "update":
					JSONObject edit = r.json(url+"/"+option1).object();
					edit.put("text_message", option2);
					System.out.println(edit);
					r.json(url+"/"+option1, put(Resty.content(edit)));
					break;
				case "delete":
					String del = "http://frozen-ravine-4990.herokuapp.com/microposts/";
					JSONResource ret = r.json(del+option1, delete());
					break;
				default:
					System.out.println("not a valid command");
						
				}
			
				ary =r.json(url).array();

			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
