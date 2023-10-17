import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
public class Artist {
	private String ID;
	private String Name;
	private String Address;
	private String Birthdate;
	private String Bio;
	private ArrayList <String> Occupations;
	private ArrayList <String> Genres;
	private ArrayList <String> Awards;
	public Artist(String id, String name, String address, String birthdate, String bio, ArrayList<String> occupations,
			ArrayList<String> genres, ArrayList<String> awards) {
		super();
		ID = id;
		Name = name;
		Address = address;
		Birthdate = birthdate;
		Bio = bio;
		Occupations = occupations;
		Genres = genres;
		Awards = awards;
	}
	
	public boolean addArtist() {
		if(getArtistIDFromFile() != -1) {
			System.out.print("Err: Artist already exists");
			return false;
		}
		if(!checkConditions()) return false;
		return writeToFile();
	}
	
	public boolean updateArtist() {
		Artist oldArtist;
		if((oldArtist = getArtistFromFile()) == null) {
			System.out.print("Err:  Artist does not exists");
			return false;
		}
		
		//Condition 1
		if(!checkConditions()) return false;
		
		//Condition 2
		if(getBirthdayYear(oldArtist.Birthdate) < 2000 && !oldArtist.Occupations.equals(Occupations)) {
			System.out.println("Err: Artist is to old to change occupation");
			return false;
		}
		
		//Condition 3
		if(!(oldArtist.Awards.size() == 0 && Awards.size() == 0) && !oldArtist.Awards.equals(Awards)) {
			for(String award : oldArtist.Awards){
				if(getAwardYear(award) < 2000) {
					if(!Awards.contains(award)) {
						System.out.println("Err: An old award can not be changed");
					}
				}
			}
		}
		
		replaceArtistInFile();
		
		return true;
	}
	
	private boolean checkConditions() {
		
		if(!checkId()) return false;
		if(!checkBirthday()) return false;
		if(!checkAddress()) return false;
		if(!checkBio()) return false;
		if(!checkOccupation()) return false;
		if(!checkAwards()) return false;
		if(!checkGeres()) return false;
		return true;
	}
	
	private boolean checkId() {
		//Id exactly 10 characters long
		if(ID.length() != 10) {
			System.out.println("Err: Wrong ID Length"); 
			return false;
		}
		
		//first three characters are numbers between 5 - 9
		//4th - 8th should be uppercase letters (A-Z)
		//Last two should be special characters 
		if(!ID.matches("[5-9][5-9][5-9][A-Z]{5}[^a-zA-Z0-9]{2}")) {
			System.out.println("Err: Wrong ID Format"); 
			return false;
		}
		
		
		return true;
	}
	
	private boolean checkBirthday() {
		//Brithday needs to be in the format DD-MM-YYYY
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false); // This ensures strict date parsing

        try {
            Date date = sdf.parse(Birthdate);
            return true; 
        } catch (ParseException e) {
        	System.out.println("Err: Wrong Date Format"); 
        	return false;
        }
	}
	
	private boolean checkAddress() {
		//Adress format is City|State|Country
		if(Address.split("\\|").length != 3) {
        	System.out.println("Err: Wrong Address Format"); 
			return false;
		}
		return true;
	}
	
	private boolean checkBio() {
		//Bio is between 10 to 30 
		String[] words = Bio.split("\\s+");
        int wordCount = words.length;

        if (wordCount >= 10 && wordCount <= 30) {
        	return true;
        }
        
    	System.out.println("Err: Wrong Number of bio words"); 
		return false;
	}
	
	private boolean checkOccupation() {
		//between 1 -5 occupations
		if(Occupations.size() < 1 || Occupations.size() > 5) {
        	System.out.println("Err: Wrong Number of occupations");
        	return false;
		}
		
		return true;
	}
	
	private boolean checkAwards() {
		//betwwen 0 - 3 awards
		if(Awards.size() < 0 || Awards.size() > 3) {
        	System.out.println("Err: Wrong Number of awards");
        	return false;
		}
		
		//awards are in the format Year, Title
		for (String award : Awards) {
			String[] splitted = award.split(",");
			if(splitted.length != 2) {
				System.out.println("Err: Wrong Award format");
	        	return false;
			}
			
			//the title is between 4 - 10 words
			String[] splittedTitle = splitted[1].replaceFirst("^\\s+", "").split("\\s+");
			if(splittedTitle.length < 4 || splittedTitle.length > 10) {
				System.out.println("Err: Wrong Number of words in award title");
	        	return false;
			}
		}
		
		return true;
	}
	
	private boolean checkGeres() {
		//between 2 - 5 genres
		if(Genres.size() < 2 || Genres.size() > 5) {
        	System.out.println("Err: Wrong Number of genres");
        	return false;
		}
		
		//pop and rock can not be at the same time
		if(Genres.contains("rock") && Genres.contains("pop")) {
        	System.out.println("Err: Wrong genre combination");
			return false;
		}
		return true;
	}
	
	private boolean writeToFile() {
		try {
            // Create a FileWriter in append mode (true as the second parameter)
            FileWriter fileWriter = new FileWriter("./Artist.txt", true);

            // Wrap FileWriter in a BufferedWriter for improved performance
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Append the line to the file
            bufferedWriter.write(this.toString());
            bufferedWriter.newLine(); // Add a newline character
            
            
            // Close the BufferedWriter and FileWriter
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
		System.out.println("Write succes");
		return true;
	}

	@Override
	public String toString() {
		String res = "Artist [ID=" + ID + ";Name=" + Name + ";Address=" + Address + ";Birthdate=" + Birthdate + ";Bio="
				+ Bio + ";Occupations=" + Occupations + ";Genres=" + Genres + ";Awards=[";
		String separator = " | "; // Replace with your custom separator

        // Convert the ArrayList to a single string with the custom separator
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Awards.size(); i++) {
            result.append(Awards.get(i));
            if (i < Awards.size() - 1) {
                result.append(separator);
            }
        }

        res += result.toString();
        res += "]]";
        
        return res;
	}
	
	private int getArtistIDFromFile() {
		int lineCount = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("./Artist.txt"));
            
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
            	if(getID(line).equals(ID)) {
            		return lineNo;
            	}
            	lineNo++;
            }
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        
        return -1;
	}
	
	private String getID(String line) {
		return line.substring(11,21);
	}
	
	private Artist getArtistFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Artist.txt"));
            
            String line;
            while ((line = br.readLine()) != null) {
            	if(getID(line).equals(ID)) {
            		String data = line.substring(8, line.length() - 1);
            		String[] keyValuePairs = data.split(";");

                    // Create an array to store the values
                    String[] values = new String[keyValuePairs.length];

                    //parse line to elements
                    // Extract the values after the "=" character
                    for (int i = 0; i < keyValuePairs.length; i++) {
                        String[] parts = keyValuePairs[i].split("=");
                        if (parts.length == 2) {
                            values[i] = parts[1];
                        } else {
                            // Handle cases where the format is incorrect
                            values[i] = "N/A"; // or any other suitable value
                        }
                    }
                    
                     return new Artist(values[0], values[1], values[2], values[3], values[4], stringToArrayList(values[5]), stringToArrayList(values[6]), awardSreingToArrayList(values[7]));
            	}
            }
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return null;
	}
	
	private ArrayList<String> stringToArrayList(String string){
		 // Remove brackets and split by commas
        String[] arr = string.replace("[", "").replace("]", "").split(", ");
        
        // Convert the array to an ArrayList
        return new ArrayList<>(Arrays.asList(arr));
	}
	
	private ArrayList<String> awardSreingToArrayList(String award){
		 // Remove brackets and split by commas
        String[] arr = award.replace("[", "").replace("]", "").split("\\| ");

        if(arr.length == 1 && arr[0].equals("")) {
        	return new ArrayList<String>();
        }
        // Convert the array to an ArrayList
        return new ArrayList<>(Arrays.asList(arr));
	}
	
	private int getBirthdayYear(String date) {
		String year = date.substring(date.length()-4, date.length());
		return Integer.parseInt(year);
	}
	
	private int getAwardYear(String award) {
		String[] splitted = award.split(",");
		return Integer.parseInt(splitted[0]);
	}
	
	private void replaceArtistInFile() {
		List<String> fileContent = new ArrayList<>();
		int lineNo = -1;
		try {
            BufferedReader br = new BufferedReader(new FileReader("./Artist.txt"));
            int currentLineNo = 0;
            String line;
            while ((line = br.readLine()) != null) {
            	fileContent.add(line);
            	if(getID(line).equals(ID)) {
            		lineNo = currentLineNo;
            	}
            	currentLineNo++;
            }
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
		
		if(lineNo == -1) {
			System.err.println("Something went wrong");
		}
		
		fileContent.set(lineNo, this.toString());
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Artist.txt"))) {
            for (String line : fileContent) {
                bw.write(line);
                bw.newLine(); // Add a newline character
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return;
	}
	
	
	
}
