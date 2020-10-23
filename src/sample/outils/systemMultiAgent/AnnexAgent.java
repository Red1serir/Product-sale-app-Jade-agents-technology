package sample.outils.systemMultiAgent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sample.Main;
import sample.outils.engine.MoteurInference;
import sample.outils.logic.BaseConnaissance;
import sample.outils.logic.Regle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class AnnexAgent extends Agent {

    private HashMap<String,String> request;
    private MoteurInference moteurInference;
    private String queryCondition ;
    @Override
    protected void setup() {

        //recevoire la requete
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage requestMessage = receive();
                if (requestMessage != null){
                    System.out.println(2);
                    try {
                        request = (HashMap<String, String>)requestMessage.getContentObject();

                        moteurInference = new MoteurInference(request, getBaseConnaissance(request.get("category")));
                        queryCondition = moteurInference.forwardChain().getQuery();
                        System.out.println("query ))))))))))))))))))) : "+queryCondition);

                        ACLMessage reply =requestMessage.createReply();
                        reply.setContent(queryCondition);
                        send(reply);
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                }else block();
            }
        });

    }

    private BaseConnaissance getBaseConnaissance(String agentCategory){
        //load la base des règles de moteur d'inférance

        String sql = "SELECT regle FROM baseConnaissance where fk_objet = '" + agentCategory +"';";
        try (Connection conn = Main.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            BaseConnaissance baseConnaissance = new BaseConnaissance();
            // loop through the result set
            while (rs.next()) {
                baseConnaissance.add(new Regle(rs.getString("regle")));
            }
            return baseConnaissance;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
