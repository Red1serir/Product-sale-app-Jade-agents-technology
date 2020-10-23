package sample.outils.systemMultiAgent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import sample.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BuildAgents {

    private ContainerController containerController;
    private AgentController centralAgent;
    private ArrayList<AgentController> annexAgents;
    private boolean isBuildingCentralAgent;

    public BuildAgents() {

        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter("verbosity","1");
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        profile.setParameter(Profile.GUI,"true");
        this.containerController = runtime.createMainContainer(profile);

    }


    public AgentController buildCentralAgent(Object[] args){
        isBuildingCentralAgent = true;
        try {

            String sql = "SELECT distinct nom FROM objet;";
            try (Connection conn = Main.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){


                while (rs.next()) {
                    AgentController secondagentController = this.containerController.createNewAgent(
                            "annex_agent_"+rs.getString("nom"),
                            AnnexAgent.class.getName(),
                            null
                    );
                    secondagentController.start();
                }

                this.centralAgent = this.containerController.createNewAgent(
                        "central_agent",
                        CentralAgent.class.getName(), args);
                this.centralAgent.start();





            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


            return this.centralAgent;
        }catch (StaleProxyException spe){
            spe.printStackTrace();
            return null;
        }
    }



    public AgentController getCentralAgent() {
        return centralAgent;
    }

    public ArrayList<AgentController> getAnnexAgents() {
        return annexAgents;
    }

}
