package org.janusgraph.graphdb.database.serialize.attribute;

import org.janusgraph.core.attribute.AttributeSerializer;
import org.janusgraph.core.schema.Parameter;
import org.janusgraph.diskstorage.ScanBuffer;
import org.janusgraph.diskstorage.WriteBuffer;
import org.janusgraph.graphdb.database.serialize.DataOutput;
import org.janusgraph.graphdb.database.serialize.Serializer;
import org.janusgraph.graphdb.database.serialize.SerializerInjected;

import java.lang.reflect.Array;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public class ParameterArraySerializer extends ArraySerializer implements AttributeSerializer<Parameter[]>, SerializerInjected {

    private Serializer serializer;

    @Override
    public Parameter[] convert(Object value) {
        return convertInternal(value, null, Parameter.class);
    }

    @Override
    protected Object getArray(int length) {
        return new Parameter[length];
    }

    @Override
    protected void setArray(Object array, int pos, Object value) {
        Array.set(array, pos, ((Parameter) value));
    }

    //############### Serialization ###################

    @Override
    public Parameter[] read(ScanBuffer buffer) {
        int length = getLength(buffer);
        if (length<0) return null;
        Parameter[] result = new Parameter[length];
        for (int i = 0; i < length; i++) {
            result[i]=serializer.readObjectNotNull(buffer,Parameter.class);
        }
        return result;
    }

    @Override
    public void write(WriteBuffer buffer, Parameter[] attribute) {
        writeLength(buffer,attribute);
        if (attribute!=null) for (int i = 0; i < attribute.length; i++) ((DataOutput)buffer).writeObjectNotNull(attribute[i]);
    }


    @Override
    public void setSerializer(Serializer serializer) {
        this.serializer=serializer;
    }
}