/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cli;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Necho
 */
public class CLI { 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        boolean sw=true;//se hace false cuando el usuario escriba exit....cierra el loop while
        boolean sw2;//se hace false cuando un comando esta mal escrito
        
        System.out.println("Welcome to the CLI");

        while(sw){          
            Scanner reader = new Scanner(System.in);  
            System.out.println("\nEnter a command: ");
            String s = reader.nextLine();
            s=QuitaEspacios(s);
            sw2=true;
            
            if(AnalisisLex(s)){
                System.out.println("Lexical Analysis (General) : Success\n");
                String[] temp=s.split(" ");
                String temp2=temp[0];                                               //Tomo la primera palabra del comando
                temp2=temp2.toLowerCase();
                
                Pattern c=Pattern.compile("[a-z]([a-z]|[0-9])+");//vble
                Pattern r=Pattern.compile("\\-(l)?(1|2|3)?(c)?(f)?(v)?(b)?");//key
                Pattern x=Pattern.compile("[a-z]([a-z]|[0-9])+\\.(string|int|float|file)");//atrib
                
                //Revisa el primer token que sea una accion
                switch(temp2){
                    case "exit":
                        Scanner reader2 = new Scanner(System.in); 
                        System.out.println("Are you sure you want to exit (y/n)");
                        String answer = reader2.nextLine();
                        if(answer.equals("y")){sw=false;}
                        break;
                    case "link":                   
                        if(temp.length==4 && (temp[2].equals("contains"))){
                            System.out.println("Creating link");
                            Link(temp[1],temp[3]);
                        }else{
                            System.out.println("Error: Missing parameters");
                        }                    
                        break;
                    case "delete":                
                        if(temp.length==2){
                            System.out.println("Deleting class");
                            Delete(temp[1]);
                        }else{
                            System.out.println("Error: No class selected");
                        }
                        break;
                    case "edit":
                        if(temp.length<4 || temp.length>5){
                            System.out.println("Error: Number of tokens must be 4 or 5");
                            sw2=false;
                        }else{
                            System.out.println("Editing class");
                            String clase=temp[1]; //classname
                            String action=temp[2];// add or remove
                            action=action.toLowerCase();
                            if(action.equals("add") || action.equals("remove")){
                                    
                                        Matcher n = r.matcher(temp[3]);//revisa si es llave
                                        Matcher m = x.matcher(temp[3]);//revisa si es atributo
                                        boolean d = n.matches();
                                        boolean e = m.matches();
                                        
                                        if(action.equals("remove") && temp.length==4){
                                            if(!e){
                                                System.out.println("Error: "+temp[3]+" is not a valid atribute");
                                                sw2=false;
                                            }                                            
                                        } 
                                        if (action.equals("add") && temp.length==5){
                                            if(d){
                                                Matcher p=x.matcher(temp[4]);
                                                boolean q=p.matches();
                                                if(!q){
                                                    System.out.println("Error: "+temp[4]+" is not a valid atribute");
                                                    sw2=false;
                                                }
                                            }else{
                                                if(!e){
                                                    System.out.println("Error: "+temp[3]+" is not a valid atribute");
                                                    sw2=false;
                                                }
                                            }
                                        }

                            }else{
                                System.out.println("Error: The operation selected ("+action+") doesn't exist");
                                sw2=false;
                            }                                            
                        }
                        if(sw2){
                            Edit(temp);
                        }

                        break;
                    case "create":
                        if(temp.length==1){
                            System.out.println("Error: Incomplete command");
                            sw2=false;
                        }else{
                            if(temp.length>2){
                                System.out.println("Error: Too many parameters");
                                sw2=false;
                            }else{
                                String classname=temp[1];
                                System.out.println("Creating class "+classname);
                                boolean sw3=true;
                                String test="create "+classname;
                                
                                while(sw3){
                                    Scanner r2 = new Scanner(System.in);  
                                    System.out.println("\nEnter an atribute : ");
                                    String s2 = reader.nextLine();
                                    s2=QuitaEspacios(s2);
                                    String[] atribs=s2.split(" ");
                                    
                                    Pattern r3=Pattern.compile("\\-(l)?(1|2|3)?(c)?(f)?(v)?(b)?");//key
                                    Pattern x3=Pattern.compile("[a-z]([a-z]|[0-9])+\\.(string|int|float|file)");//atrib
                                    
                                    if(atribs.length==1 ){
                                        if(atribs[0].equals("end")){
                                            System.out.println("\n"+test);
                                            sw3=false;
                                        }else{
                                            Matcher tester = x3.matcher(atribs[0]);//revisa si es atributo
                                            boolean d = tester.matches();
                                            if(d){
                                                test+=" "+atribs[0];
                                            }else{
                                                System.out.println("No es un atributo valido");                  
                                            }
                                        }              
                                    }else{
                                        if(atribs.length==2){
                                            Matcher tester = x3.matcher(atribs[1]);//revisa si es atributo
                                            Matcher tester2=r3.matcher(atribs[0]);//key
                                            boolean d = tester.matches();
                                            boolean d2= tester2.matches();
                                            if(d && d2){
                                                test=test+" "+atribs[0]+" "+atribs[1];
                                            }else{
                                                if(!d){
                                                    System.out.println("No es un atributo valido");
                                                }
                                                if(!d2){
                                                    System.out.println("No es una llave valida");
                                                }
                                            }                                         
                                        }else{
                                            System.out.println("Error");
                                            sw2=false;
                                        }
                                    }
                                }                            
                            }
                        }
                        if(sw2){
                               Create(s);     
                        }
                        break;
                        
                    default:
                        System.out.println("The command "+temp2+" doesn´t exist");
                        break;
                    
                }
            }else{
                System.out.println("Lexical Analysis (General): Failed\n");
            }          
        }               
    }
    
    private static String QuitaEspacios(String comando){
        //quita los espacios al comienzo y final del comando
        //quita los espacios repetidos en medio del comando
        
        while(comando.startsWith(" ")){
            comando=comando.substring(1);
        }
        
        while(comando.endsWith(" ")){
            int t=comando.length();
            comando=comando.substring(0, t-1);
        }
        
        char[] test2=comando.toCharArray();
        boolean sw=true;
        String result="";
        
        for(int i=0;i<test2.length;i++){
            char p=test2[i];
            if(p!=' '){
                if(!sw){
                    sw=true;
                }                
                result+=p;
            }else{
                if(sw){
                    result+=p;
                    sw=false;
                }
            }    
        }
               
        return result;
    }
    
    private static boolean AnalisisLex(String command){
        command=command.toLowerCase();
        String[] temp=command.split(" ");
        boolean sw=false;
        
        //chequea token por token
        for(int j=0;j<temp.length;j++){
            sw=false;
            //chequea si el token es una accion
            Pattern p = Pattern.compile("(create|edit|add|remove|delete|link|contains|exit)");
            Matcher m = p.matcher(temp[j]);
            boolean b = m.matches();          
            if(b){
                //System.out.println("\t"+temp[j]+" Si es una accion valida");
                sw=true;
            }else{
                //System.out.println("\t"+temp[j]+" No es una accion valida");
            }
            
            //chequea si el token es un nombre de clase
            Pattern c=Pattern.compile("[a-z]([a-z]|[0-9])*");
            Matcher n = c.matcher(temp[j]);
            boolean d = n.matches();  
            if(d){
                //System.out.println("\t"+temp[j]+" Si es un nombre de clase valido");
                sw=true;
            }else{
                //System.out.println("\t"+temp[j]+" No es un nombre de clase valido");
            }
            
            //chequea si es un atributo
            Pattern x=Pattern.compile("[a-z]([a-z]|[0-9])*\\.(string|int|float|file)");
            Matcher y = x.matcher(temp[j]);
            boolean z= y.matches();
            if(z){
                //System.out.println("\t"+temp[j]+" Si es un atributo valido");
                sw=true;
            }else{
                //System.out.println("\t"+temp[j]+" No es un atributo valido");
            }
            
            //chequea si es un grupo de keys    (123)?(c)?(f)?(v)?(b)?
            Pattern r=Pattern.compile("\\-(l)?(b)?(1|2|3)?(c)?(f)?(v)?");
            Matcher s = r.matcher(temp[j]);
            boolean t= s.matches();
            if(t){
                //System.out.println("\t"+temp[j]+" Si es una llave valida");
                sw=true;
            }else{
                //System.out.println("\t"+temp[j]+" No es una llave valida");
            }
            
            if(sw){
                System.out.println("Lexical Analysis token#"+j+" : Success");
            }else{
                System.out.println("Lexical Analysis token#"+j+": Failed");
                return sw;
            }
        } 

        return sw;
    }
    
    private static void Create(String command){
        System.out.println("Command successfully executed");      
    }
    
    private static void Edit(String[] command){
        String classname=command[1];
        String action=command[2];
        System.out.println("Command successfully executed");         
    }
    
    private static void Link(String classname1, String classname2){
        System.out.println("Command successfully executed");  
    }
    
    private static void Delete(String classname){
        System.out.println("Command successfully executed");       
   }
}
