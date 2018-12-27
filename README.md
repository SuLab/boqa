# BOQA

This is a fork of [BOQA](https://github.com/Phenomics/boqa) that adds a command line interface and standalone installation.

**Requires**:
- hp.obo (Download [here](http://purl.obolibrary.org/obo/hp.obo))
- phenotype_annotation.tab (Download [here](https://hpo.jax.org/app/download/annotation))

To use a Wikidata-supplemented phenotype annotation file, follow the instructions [here](https://github.com/SuLab/Wikidata-phenomizer).


**Installation:**

`mvn install`

jar file will be in `target` directory

**Simple usage by:**

```
cd target
java -jar boqa-0.0.3-SNAPSHOT.jar -hpo {comma-separated HPO IDs} -obo path_to_obo_file -af path_to_association_file
```

**Example usage:**

`java -jar boqa-0.0.3-SNAPSHOT.jar -hpo "HP:0001263,HP:0100022,HP:0001290,HP:0000522,HP:0002353,HP:0002910" -obo hp.obo -af phenotype_annotation.tab`

**Help**
Run `java -jar boqa-0.0.3-SNAPSHOT.jar --help` to display all arguments.
