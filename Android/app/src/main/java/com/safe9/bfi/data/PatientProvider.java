package com.safe9.bfi.data;

import android.net.Uri;

import com.safe9.bfi.BuildConfig;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Content Provider for local patient data
 */
@ContentProvider(authority = PatientProvider.AUTHORITY, database = PatientDatabase.class)
public class PatientProvider {


    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;

    @TableEndpoint(table = PatientDatabase.PATIENTS)
    public static class Patients {

        @ContentUri(
                path = "patients",
                type = "vnd.android.cursor.dir/patient",
                defaultSort = PatientColumns.NAME + " ASC")
        public static final Uri URI_PATIENTS = Uri.parse("content://" + AUTHORITY + "/patients");

    }
    @TableEndpoint(table = PatientDatabase.CHILDREN)
    public static class Children {

        @ContentUri(
                path = "children",
                type = "vnd.android.cursor.dir/child",
                defaultSort = ChildColumns.AADHAAR + " ASC")
        public static final Uri URI_CHILDREN = Uri.parse("content://" + AUTHORITY + "/children");

    }


}
