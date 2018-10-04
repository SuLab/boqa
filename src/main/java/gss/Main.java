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
import ontologizer.go.Term;
import ontologizer.types.ByteString;

/**
 * Command line interface
 * @author gstupp
 */
public class Main {

    @Parameter(names = "-hpo", description = "Comma-separated list of HPO curies", splitter = CommaSplitter.class, required = true)
    private List<String> hpoIDs;

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
        //List<String> hpoIDs = Arrays.asList("HP:0000970", "HP:0000252", "HP:0001250", "HP:0004305", "HP:0025460", "HP:0100022");
        // -hpo HP:0001263,HP:0100022,HP:0001290,HP:0000522,HP:0002353,HP:0002910,HP:0000252,HP:0012705,HP:0001250,HP:0040129,HP:0002650,HP:0200055,HP:0012804

        BoqaService service = new BoqaService("hp.obo", "phenotype_annotation_wd.tab");
        service.scoreItemsForTestQuery();

        BOQACore.setAssociationFileType(Type.PAF);
        BOQACore boqaCore = new BOQACore("hp.obo", "phenotype_annotation_wd.tab");

        List<Integer> queryAsBoqaIndices = new ArrayList<>();
        //System.out.println(hpoIDs);
        for (String hpoID : hpoIDs) {
            queryAsBoqaIndices.add(boqaCore.getIdOfTerm(boqaCore.getOntology().getTerm(hpoID)));
        }

        List<ItemResultEntry> resultList = boqaCore.score(queryAsBoqaIndices);
        System.out.println("itemName|score");
        for (int i = 0; i < 10; i++) {
            int boqaId = resultList.get(i).getItemId();
            System.out.println(boqaCore.getTerm(boqaId).getIDAsString());
            String itemName = boqaCore.getItemName(boqaId);
            double score = resultList.get(i).getScore();
            System.out.println(itemName + "|" + score);
        }
    }
}
