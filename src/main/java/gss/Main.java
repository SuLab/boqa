package gss;

import com.beust.jcommander.JCommander;
import drseb.BoqaService;
import java.util.ArrayList;
import java.util.List;
import ontologizer.association.AssociationParser.Type;
import sonumina.boqa.server.BOQACore;
import sonumina.boqa.server.ItemResultEntry;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.IParameterSplitter;
import java.util.Arrays;

/**
 * Command line interface
 *
 * @author gstupp
 */
public class Main {

    @Parameter(names = "-hpo", description = "Comma-separated list of HPO curies", splitter = CommaSplitter.class, required = true)
    private List<String> hpoIDs;

    @Parameter(names = "-obo", description = "Path to ontology OBO file", required = true)
    private String oboFile;

    @Parameter(names = "-af", description = "Path to association file", required = true)
    private String assFile;
    
    @Parameter(names = "-n", description = "Number of results to display")
    private int numResults = 10;

    public static class CommaSplitter implements IParameterSplitter {

        public List<String> split(String value) {
            return Arrays.asList(value.split(","));
        }
    }

    public static void main(String argv[]) {

        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(argv);
        main.run();
    }

    public void run() {

        BoqaService service = new BoqaService(oboFile, assFile);
        service.scoreItemsForTestQuery();

        BOQACore.setAssociationFileType(Type.PAF);
        BOQACore boqaCore = new BOQACore(oboFile, assFile);

        List<Integer> queryAsBoqaIndices = new ArrayList<>();
        //System.out.println(hpoIDs);
        for (String hpoID : hpoIDs) {
            queryAsBoqaIndices.add(boqaCore.getIdOfTerm(boqaCore.getOntology().getTerm(hpoID)));
        }

        List<ItemResultEntry> resultList = boqaCore.score(queryAsBoqaIndices);
        System.out.println("itemName|score");
        for (int i = 0; i < numResults; i++) {
            int boqaId = resultList.get(i).getItemId();
            String itemName = boqaCore.getItemName(boqaId);
            double score = resultList.get(i).getScore();
            System.out.println(itemName + "|" + score);
        }
    }
}
