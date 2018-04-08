package com.safe9.bfi.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Columns for database table to store patient data
 */
public interface PatientColumns {

    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT)
    String NAME = "name";

    @DataType(TEXT)
    String PHONE = "phone";

    @DataType(INTEGER)
    String WEEKS = "weeks";

    @DataType(INTEGER)
    String AGE = "age";

    @DataType(INTEGER)
    String WEIGHT = "weight";

    @DataType(TEXT)
    String ADDRESS = "address";

    @DataType(TEXT)
    String AADHAAR = "aadhaar";

    @DataType(TEXT)
    String DOCTOR = "doctor";


}
