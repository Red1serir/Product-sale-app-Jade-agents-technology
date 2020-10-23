package sample.outils.systemMultiAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import sample.client.Controller;
import sample.client.ControllerRequest;
import sample.client.ControllerRequestResult;

import java.io.IOException;
import java.util.HashMap;

public class CentralAgent extends Agent {

    private HashMap<String, String> request;

    private ControllerRequestResult controllerRequestResult;

    public CentralAgent() {
        this.request = new HashMap<>();
    }

    @Override
    protected void setup() {

        Object[] args = getArguments();

        if (args!=null && args.length>0) {
            controllerRequestResult = (ControllerRequestResult) args[0];
            controllerRequestResult.setCentralAgent(this);
            request = (HashMap<String,String>) args[1];
            System.out.println(request);
        }


        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                if (request != null) {
                    System.out.println(1);
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.setContentObject(request);
                    message.addReceiver(new AID("annex_agent_" + request.get("category"), AID.ISLOCALNAME));
                    send(message);
                }else block();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //reception
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage response = receive();
                if (response != null) {
                    System.out.println(3);
                    controllerRequestResult.reciveResult(
                            request.get("category"),
                            response.getContent()
                    );
                }else block();
            }
        });




    }
}
