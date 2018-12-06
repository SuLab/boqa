# BOQA

Requires (in the current dir):
- hp.obo (Download [here](http://purl.obolibrary.org/obo/hp.obo))
- phenotype_annotation.tab (Download [here](https://hpo.jax.org/app/download/annotation))

**Simple usage by:**

```
cd target
java -jar boqa-0.0.3-SNAPSHOT.jar -hpo {comma-separated HPO IDs}
```

**Example usage:**
`java -jar boqa-0.0.3-SNAPSHOT.jar -hpo "HP:0001263,HP:0100022,HP:0001290,HP:0000522,HP:0002353,HP:0002910"`
