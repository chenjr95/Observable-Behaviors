package a.cis350project;

import com.parse.ParseObject;

/**
 * Created by socratesli on 4/11/15.
 *
 * interface to ensure that our objects which need to be stored in Parse cloud can all be converted
 * to ParseObjects
 *
 */
interface StoredInParseCloud {

    // convert this object to a ParseObject
    ParseObject toParseObject();

    // get parseObjectId of this object in the Parse cloud
    String getParseObjectId();
}
