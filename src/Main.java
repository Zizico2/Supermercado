import Exceptions.*;
import Supermarket.Supermarket;
import Supermarket.SupermarketClass;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Supermarket sP = new SupermarketClass();
        Scanner in = new Scanner(System.in);
        executeCommand(in, sP);
    }

    private enum Message {

        CART_REGISTERED("Carrinho criado com sucesso."),
        DUPLICATE_CART("Carrinho inexistente!"),
        ITEM_REGISTERED("Artigo criado com sucesso."),
        DUPLICATE_ITEM("Artigo existente!"),
        ITEM_ADDED_TO_CART("Artigo adicionado com sucesso."),
        NON_EXISTANT_CART("Carrinho inexistente!"),
        NON_EXISTANT_ITEM("Artigo inexistente!"),
        NO_MORE_SPACE("Capacidade excedida!"),
        ITEM_REMOVED("Artigo removido com sucesso."),
        CART_DOES_NOT_CONTAIN_ITEM(NON_EXISTANT_ITEM.msg.replace("!", " no carrinho!")),
        EMPTY_CART("Carrinho vazio!"),
        VOID(""),
        UNKNOWN("Opcao inexistente."),
        EXITING("Volte sempre.");


        private final String msg;

        Message(String msg) {
            this.msg = msg;
        }
    }

    private enum Command {
        REGISTER("CRIA"),
        REGISTER_CART("CARRINHO"),
        REGISTER_ITEM("ARTIGO"),
        ADD_TO_CART("DEPOSITA"),
        REMOVE_FROM_CART("REMOVE"),
        LIST_ITEMS("LISTA"),
        PAY("PAGA"),
        EXIT("SAIR"),
        UNKNOWN("");

        private final String cmd;

        Command(String cmd) {
            this.cmd = cmd;
        }
    }

    private static Command getCommand(Scanner in) {
        String cmd = in.next().toUpperCase();
        for (Command Cmd : Command.values())
            if (cmd.equals(Cmd.cmd))
                return Cmd;
        return Command.UNKNOWN;
    }

    private static void executeCommand(Scanner in, Supermarket sP) {
        Command cmd = Command.UNKNOWN;

        while (!cmd.equals(Command.EXIT)) {
            cmd = getCommand(in);

            switch (cmd) {
                case REGISTER:
                    switch (getCommand(in)) {
                        case REGISTER_CART:
                            processRegisterCart(in, sP);
                            break;
                        case REGISTER_ITEM:

                            processRegisterItem(in,sP);
                    }
                    break;
                case ADD_TO_CART:
                    processAddToCart(in, sP);
                    break;

                case REMOVE_FROM_CART:
                    processRemoveFromCart(in,sP);
                    break;

                case LIST_ITEMS:
                    processListItems(in, sP);
                    break;

                case PAY:
                    processPay(in, sP);
                    break;


                case EXIT:
                    System.out.println(Message.EXITING.msg + "\n");
                    in.close();
                    break;

                case UNKNOWN:
                    System.out.println(Message.UNKNOWN.msg);
            }
            System.out.print("\n");
        }
    }

    private static void processRegisterCart(Scanner in, Supermarket sP) {
        try {
            registerCart(in, sP);
            System.out.println(Message.CART_REGISTERED.msg);
        }
        catch (DuplicateIDException e){
            System.out.println(Message.DUPLICATE_CART.msg);
        }
    }

    private static void registerCart(Scanner in, Supermarket sP) throws DuplicateIDException {
        String ID = in.next().trim();

        int capacity = in.nextInt();
        in.nextLine();
        if(sP.hasCart(ID))
            throw new DuplicateIDException();
        sP.registerCart(ID, capacity);
    }
    private static void processRegisterItem(Scanner in, Supermarket sP) {
        try {
            registerItem(in, sP);
            System.out.println(Message.ITEM_REGISTERED.msg);
        }
        catch (DuplicateIDException e){
            System.out.println(Message.DUPLICATE_ITEM.msg);
        }
    }

    private static void registerItem(Scanner in, Supermarket sP) throws DuplicateIDException {
        String ID = in.next().trim();
        int price = in.nextInt();
        int volume = in.nextInt();
        in.nextLine();

        if(sP.hasItem(ID))
            throw new DuplicateIDException();
        sP.registerItem(ID,price, volume);
    }

    private static void processAddToCart(Scanner in, Supermarket sP) {
        try{
            addToCart(in,sP);
            System.out.println(Message.ITEM_ADDED_TO_CART.msg);
        } catch(CartIDNotFoundException e){
            System.out.println(Message.NON_EXISTANT_CART.msg);
        }
        catch(ItemIDNotFoundException e){
            System.out.println(Message.NON_EXISTANT_ITEM.msg);
        }
        catch(CapacityExcededException e){
            System.out.println(Message.NO_MORE_SPACE.msg);
        }
    }

    private static void addToCart(Scanner in, Supermarket sP) throws CartIDNotFoundException,ItemIDNotFoundException,CapacityExcededException{
        String itemID = in.next().trim();
        String cartID = in.nextLine().trim();
        if(!sP.hasCart(cartID))
            throw new CartIDNotFoundException();
        if(!sP.hasItem(itemID))
            throw new ItemIDNotFoundException();
        if(!sP.enoughSpace(itemID,cartID))
            throw  new CapacityExcededException();

        sP.addToCart(itemID,cartID);
    }

    private static void processRemoveFromCart(Scanner in, Supermarket sP) {
        try{
            removeFromCart(in,sP);
            System.out.println(Message.ITEM_REMOVED.msg);
        } catch(CartIDNotFoundException e){
            System.out.println(Message.NON_EXISTANT_CART.msg);
        }
        catch(ItemIDNotFoundException e){
            System.out.println(Message.CART_DOES_NOT_CONTAIN_ITEM.msg);
        }
    }

    private static void removeFromCart(Scanner in,Supermarket sP) throws  CartIDNotFoundException,ItemIDNotFoundException{
        String itemID = in.next().trim();
        String cartID = in.nextLine().trim();

        if(!sP.hasCart(cartID))
            throw new CartIDNotFoundException();
        if(!sP.hasItemInCart(cartID,itemID))
            throw new ItemIDNotFoundException();
        sP.removeFromCart(itemID,cartID);
    }

    private static void processListItems(Scanner in, Supermarket sP) {
        try{
            listItems(in,sP);
        } catch(CartIDNotFoundException e){
            System.out.println(Message.NON_EXISTANT_CART.msg);
        }
        catch(EmptyCartException e){
            System.out.println(Message.EMPTY_CART.msg);
        }
    }

    private static void listItems(Scanner in, Supermarket sP) throws CartIDNotFoundException,EmptyCartException{
        String cartID = in.nextLine().trim();

        if(!sP.hasCart(cartID))
            throw new CartIDNotFoundException();
        String msg = sP.listItems(cartID);

        if(msg.equals(Message.VOID.msg))
            throw new EmptyCartException();
        System.out.print(msg);
    }

    private static void processPay(Scanner in, Supermarket sP) {
        try{
            Pay(in,sP);
        }  catch(CartIDNotFoundException e){
            System.out.println(Message.NON_EXISTANT_CART.msg);
        }
        catch(EmptyCartException e){
            System.out.println(Message.EMPTY_CART.msg);
        }
    }

    private static void Pay(Scanner in, Supermarket sP) throws CartIDNotFoundException,EmptyCartException {
        String cartID = in.nextLine();

        if(!sP.hasCart(cartID))
            throw new CartIDNotFoundException();
        int total = sP.pay(cartID);

        if(total == 0)
            throw new EmptyCartException();
        System.out.println(total);
    }
}
