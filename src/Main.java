import java.io.*;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<instanceHandler> list = new ArrayList<instanceHandler>();
		try{
			parse(list);
			System.out.println("Please type in a specific date for the count of open and closed tasks. (format yyyy-mm-dd)");
			String input="";
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
			int openCount = 0;
			int closeCount = 0;
			for  (instanceHandler h : list){
				if (h.getCreateDate().substring(0, 10).equals(input)){
					openCount++;
				}
				if (!h.getCloseDate().equals("null") && h.getCloseDate().substring(0, 10).equals(input)){
					closeCount++;
				}
			}
			System.out.println("Number of tasks opened: " + openCount);
			System.out.println("Number of tasks closed: " + closeCount);
			System.out.println("Please give a starting date for tasks opened and closed within. (format yyyy-mm-dd)");
			input = br.readLine();
			int OpenYear = Integer.parseInt(input.substring(0, 4));
			int OpenMonth = Integer.parseInt(input.substring(5, 7));
			int OpenDay = Integer.parseInt(input.substring(8, 10));
			String input2 = "";
			System.out.println("Please give an ending date for tasks opened and closed within. (format yyyy-mm-dd)");
			input2 = br.readLine();
			int CloseYear = Integer.parseInt(input2.substring(0, 4));
			int CloseMonth = Integer.parseInt(input2.substring(5, 7));
			int CloseDay = Integer.parseInt(input2.substring(8, 10));
			openCount = 0;
			closeCount=0;
			for  (instanceHandler h : list){
				String openDate = h.getCreateDate();
				int hOpenYear = Integer.parseInt(openDate.substring(0, 4));
				int hOpenMonth = Integer.parseInt(openDate.substring(5, 7));
				int hOpenDay = Integer.parseInt(openDate.substring(8, 10));
				if(OpenYear <= hOpenYear && hOpenYear <= CloseYear){
					if(OpenMonth <= hOpenMonth && hOpenMonth <= CloseMonth){
						if(OpenDay <= hOpenDay && hOpenDay < CloseDay){
							openCount++;
						}
					}
				}
				String closeDate = h.getCloseDate();
				if(closeDate.equals("null")) continue;
				int hCloseYear = Integer.parseInt(closeDate.substring(0, 4));
				int hCloseMonth = Integer.parseInt(closeDate.substring(5, 7));
				int hCloseDay = Integer.parseInt(closeDate.substring(8, 10));
				if(OpenYear <= hCloseYear && hCloseYear <= CloseYear){
					if(OpenMonth <= hCloseMonth && hCloseMonth <= CloseMonth){
						if(OpenDay <= hCloseDay && hCloseDay < CloseDay){
							closeCount++;
						}
					}
				}
			}
			System.out.println("Number of tasks opened: " + openCount);
			System.out.println("Number of tasks closed: " + closeCount);
			System.out.println("Please give an instance ID for most recent task.");
			input = br.readLine();
			int minYear = 9999;
			int minMonth = 12;
			int minDay = 31;
			int minHour = 24;
			int minMin = 59;
			int minSec = 59;
			String ans = "";
			for  (instanceHandler h : list){
				if(!h.getInstanceId().equals(input)){
					continue;
				}
				String openDate = h.getCreateDate();
				int hOpenYear = Integer.parseInt(openDate.substring(0, 4));
				int hOpenMonth = Integer.parseInt(openDate.substring(5, 7));
				int hOpenDay = Integer.parseInt(openDate.substring(8, 10));
				int hOpenHour = Integer.parseInt(openDate.substring(11, 13));
				int hOpenMin = Integer.parseInt(openDate.substring(14, 16));
				int hOpenSec = Integer.parseInt(openDate.substring(17, 19));
				if(hOpenYear <= minYear){
					ans = h.getName();
					minYear = hOpenYear;
					minMonth = 12;
					minDay = 31;
					minHour = 24;
					minMin = 59;
					minSec = 59;
					if(hOpenMonth <= minMonth){
						ans = h.getName();
						minMonth = hOpenMonth;
						minDay = 31;
						minHour = 24;
						minMin = 59;
						minSec = 59;
						if(hOpenDay <= minDay){
							ans = h.getName();
							minDay = hOpenDay;
							minHour = 24;
							minMin = 59;
							minSec = 59;
							if(hOpenHour <= minHour){
								ans = h.getName();
								minHour = hOpenHour;
								minMin = 59;
								minSec = 59;
								if(hOpenMin <= minMin){
									ans = h.getName();
									minMin = hOpenMin;
									minSec = 59;
									if(hOpenSec <= minSec){
										ans = h.getName();
										minSec = hOpenSec;
									}
								}
							}
						}
					}
				}
			}
			System.out.println("Most Recently opened task for instance ID " + input + " is : " + ans);

			System.out.println("Please give an instance ID for number of tasks.");
			input = br.readLine();
			int count =0;
			for  (instanceHandler h : list){
				if(h.getInstanceId().equals(input)){
					count++;
				}
			}
			System.out.println("Number of tasks for instance ID " + input + " is : " + count);

			System.out.println("Please type in an assignee for count of open and closed tasks.");
			input = br.readLine();
			openCount = 0;
			closeCount = 0;
			for  (instanceHandler h : list){
				if(h.getAssignee().equals(input)){
					openCount ++;
					if (!h.getCloseDate().equals("null")){
						closeCount++;
					}
				}
			}
			System.out.println("Number of tasks opened for " + input + " : " + openCount);
			System.out.println("Number of tasks closed for " + input + " : " + closeCount);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public static void parse(ArrayList<instanceHandler> list){
		try {
			FileReader fr = new FileReader("task-2.json");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			boolean variable = false;
			int i = 0;
			while((line = br.readLine())!=null){
				line = line.replace(" ", "");
				if (line.equals("[")){
					continue;
				}
				else if(line.substring(0, 1).equals("{")){
					instanceHandler ih = new instanceHandler();
					list.add(ih);
				}
				else if(!line.contains(",") && (line.substring(0, 1).equals("}") || line.substring(0, 1).equals("]"))){
					continue;
				}
				else if(line.substring(0, 2).equals("},")){
					if(variable){
						variable = false;
					}
					else{
						i++;
						if(i==678){
							int x = i;
							i=x;
						}
					}
				}
				else if(line.length()>12 && line.substring(0, 12).equals("\"variables\":")){
					if(!line.contains("},")){
						variable = true;
					}
				}
				else if(line.length()>16 && line.substring(0, 15).equals("\"instanceName\":")){
					list.get(i).setInstanceName(line.substring(16, line.lastIndexOf("\",")));
				}
				else if(line.length()>8 && line.substring(0, 7).equals("\"name\":")){
					list.get(i).setName(line.substring(8, line.lastIndexOf("\",")));
				}
				else if(line.length()>11 && line.substring(0, 10).equals("\"dueDate\":")){
					list.get(i).setDueDate(line.substring(11, line.lastIndexOf("\",")));
				}
				else if(line.length()>13 && line.substring(0, 12).equals("\"closeDate\":")){
					if(line.substring(12, 16).equals("null")){
						list.get(i).setCloseDate("null");
					}
					else {
						list.get(i).setCloseDate(line.substring(13, line.lastIndexOf("\",")));
					}
				}
				else if(line.length()>18 && line.substring(0, 17).equals("\"instanceStatus\":")){
					list.get(i).setInstanceStatus(line.substring(18, line.lastIndexOf("\",")));
				}
				else if(line.length()>14 && line.substring(0, 13).equals("\"createDate\":")){
					list.get(i).setCreateDate(line.substring(14, line.lastIndexOf("\",")));
				}
				else if(line.length()>12 && line.substring(0, 11).equals("\"assignee\":")){
					list.get(i).setAssignee(line.substring(12, line.lastIndexOf("\",")));
				}
				else if(line.length()>14 && line.substring(0, 13).equals("\"instanceId\":")){
					list.get(i).setInstanceId(line.substring(13, line.lastIndexOf(",")));
				}
			}
			br.close();
			fr.close();
		}
		catch (Exception e){
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
	}
}
