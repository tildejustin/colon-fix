## NNBSP Fix

Fixes the NNBSP character in DateFormat outputs in Java 20+ as caused by the CLDR Unicode v42 update. This was fixed in 23w17a (1.20-alpha.23.17.a) though a font rendering revamp and was introduced in 11w49a (1.1-alpha.11.49.a), the same version that put the Save and Quit to Title button into title case.
Another way of fixing this would be to set the java property `-Djava.locale.providers=JRE` (or `COMPAT`, they are the same), but that legacy locale provider is set to be removed in the future.
