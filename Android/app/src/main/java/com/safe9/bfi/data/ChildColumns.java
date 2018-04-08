package com.safe9.bfi.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface ChildColumns {

    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT)
    String AADHAAR = "aadhaar";

    @DataType(TEXT)
    String BIRTHDATE = "birthdate";

    @DataType(TEXT)
    String HEPB1 = "hepb1";

    @DataType(TEXT)
    String HEPB6 = "hepb6";

    @DataType(TEXT)
    String TET2 = "tet2";

    @DataType(TEXT)
    String TET4 = "tet4";

    @DataType(TEXT)
    String TET6 = "tet6";

    @DataType(TEXT)
    String TET12 = "tet12";

    @DataType(TEXT)
    String TET18 = "tet18";

    @DataType(TEXT)
    String PNEUM2 = "pneum2";
    @DataType(TEXT)
    String PNEUM4 = "pneum4";
    @DataType(TEXT)
    String PNEUM12 = "pneum12";

    @DataType(TEXT)
    String ROTA2 = "rota2";
    @DataType(TEXT)
    String ROTA4 = "rota4";
    @DataType(TEXT)
    String ROTA6 = "rota6";

}
