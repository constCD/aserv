/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package tbase_test;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "cast", "rawtypes", "serial", "unchecked" })
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-12-20")
public class PushService {

    public interface Iface {

        public void setting(TPlatformFrom platformFrom) throws org.apache.thrift.TException;
    }

    public interface AsyncIface {

        public void setting(TPlatformFrom platformFrom, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;
    }

    public static class Client extends org.apache.thrift.TServiceClient implements Iface {

        public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {

            public Factory() {
            }

            public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
                return new Client(prot);
            }

            public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
                return new Client(iprot, oprot);
            }
        }

        public Client(org.apache.thrift.protocol.TProtocol prot) {
            super(prot, prot);
        }

        public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
            super(iprot, oprot);
        }

        public void setting(TPlatformFrom platformFrom) throws org.apache.thrift.TException {
            send_setting(platformFrom);
        }

        public void send_setting(TPlatformFrom platformFrom) throws org.apache.thrift.TException {
            setting_args args = new setting_args();
            args.setPlatformFrom(platformFrom);
            sendBaseOneway("setting", args);
        }
    }

    public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {

        public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {

            private org.apache.thrift.async.TAsyncClientManager clientManager;

            private org.apache.thrift.protocol.TProtocolFactory protocolFactory;

            public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
                this.clientManager = clientManager;
                this.protocolFactory = protocolFactory;
            }

            public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
                return new AsyncClient(protocolFactory, clientManager, transport);
            }
        }

        public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
            super(protocolFactory, clientManager, transport);
        }

        public void setting(TPlatformFrom platformFrom, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
            checkReady();
            setting_call method_call = new setting_call(platformFrom, resultHandler, this, ___protocolFactory, ___transport);
            this.___currentMethod = method_call;
            ___manager.call(method_call);
        }

        public static class setting_call extends org.apache.thrift.async.TAsyncMethodCall {

            private TPlatformFrom platformFrom;

            public setting_call(TPlatformFrom platformFrom, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
                super(client, protocolFactory, transport, resultHandler, true);
                this.platformFrom = platformFrom;
            }

            public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
                prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("setting", org.apache.thrift.protocol.TMessageType.ONEWAY, 0));
                setting_args args = new setting_args();
                args.setPlatformFrom(platformFrom);
                args.write(prot);
                prot.writeMessageEnd();
            }

            public void getResult() throws org.apache.thrift.TException {
                if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
                    throw new IllegalStateException("Method call not finished!");
                }
                org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
                org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
            }
        }
    }

    public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {

        private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());

        public Processor(I iface) {
            super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
        }

        protected Processor(I iface, Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
            super(iface, getProcessMap(processMap));
        }

        private static <I extends Iface> Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
            processMap.put("setting", new setting());
            return processMap;
        }

        public static class setting<I extends Iface> extends org.apache.thrift.ProcessFunction<I, setting_args> {

            public setting() {
                super("setting");
            }

            public setting_args getEmptyArgsInstance() {
                return new setting_args();
            }

            protected boolean isOneway() {
                return true;
            }

            public org.apache.thrift.TBase getResult(I iface, setting_args args) throws org.apache.thrift.TException {
                iface.setting(args.platformFrom);
                return null;
            }
        }
    }

    public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {

        private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());

        public AsyncProcessor(I iface) {
            super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
        }

        protected AsyncProcessor(I iface, Map<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>> processMap) {
            super(iface, getProcessMap(processMap));
        }

        private static <I extends AsyncIface> Map<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>> getProcessMap(Map<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>> processMap) {
            processMap.put("setting", new setting());
            return processMap;
        }

        public static class setting<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, setting_args, Void> {

            public setting() {
                super("setting");
            }

            public setting_args getEmptyArgsInstance() {
                return new setting_args();
            }

            public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
                final org.apache.thrift.AsyncProcessFunction fcall = this;
                return new AsyncMethodCallback<Void>() {

                    public void onComplete(Void o) {
                    }

                    public void onError(Exception e) {
                    }
                };
            }

            protected boolean isOneway() {
                return true;
            }

            public void start(I iface, setting_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
                iface.setting(args.platformFrom, resultHandler);
            }
        }
    }

    public static class setting_args implements org.apache.thrift.TBase<setting_args, setting_args._Fields>, java.io.Serializable, Cloneable, Comparable<setting_args> {

        private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("setting_args");

        private static final org.apache.thrift.protocol.TField PLATFORM_FROM_FIELD_DESC = new org.apache.thrift.protocol.TField("platformFrom", org.apache.thrift.protocol.TType.STRUCT, (short) 1);

        private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

        static {
            schemes.put(StandardScheme.class, new setting_argsStandardSchemeFactory());
            schemes.put(TupleScheme.class, new setting_argsTupleSchemeFactory());
        }

        // required
        public TPlatformFrom platformFrom;

        /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
        public enum _Fields implements org.apache.thrift.TFieldIdEnum {

            PLATFORM_FROM((short) 1, "platformFrom");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
            public static _Fields findByThriftId(int fieldId) {
                switch(fieldId) {
                    case // PLATFORM_FROM
                    1:
                        return PLATFORM_FROM;
                    default:
                        return null;
                }
            }

            /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null)
                    throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            private final short _thriftId;

            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

        // isset id assignments
        public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

        static {
            Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.PLATFORM_FROM, new org.apache.thrift.meta_data.FieldMetaData("platformFrom", org.apache.thrift.TFieldRequirementType.DEFAULT, new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TPlatformFrom.class)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(setting_args.class, metaDataMap);
        }

        public setting_args() {
        }

        public setting_args(TPlatformFrom platformFrom) {
            this();
            this.platformFrom = platformFrom;
        }

        /**
     * Performs a deep copy on <i>other</i>.
     */
        public setting_args(setting_args other) {
            if (other.isSetPlatformFrom()) {
                this.platformFrom = new TPlatformFrom(other.platformFrom);
            }
        }

        public setting_args deepCopy() {
            return new setting_args(this);
        }

        @Override
        public void clear() {
            this.platformFrom = null;
        }

        public TPlatformFrom getPlatformFrom() {
            return this.platformFrom;
        }

        public setting_args setPlatformFrom(TPlatformFrom platformFrom) {
            this.platformFrom = platformFrom;
            return this;
        }

        public void unsetPlatformFrom() {
            this.platformFrom = null;
        }

        /** Returns true if field platformFrom is set (has been assigned a value) and false otherwise */
        public boolean isSetPlatformFrom() {
            return this.platformFrom != null;
        }

        public void setPlatformFromIsSet(boolean value) {
            if (!value) {
                this.platformFrom = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch(field) {
                case PLATFORM_FROM:
                    if (value == null) {
                        unsetPlatformFrom();
                    } else {
                        setPlatformFrom((TPlatformFrom) value);
                    }
                    break;
            }
        }

        public Object getFieldValue(_Fields field) {
            switch(field) {
                case PLATFORM_FROM:
                    return getPlatformFrom();
            }
            throw new IllegalStateException();
        }

        /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }
            switch(field) {
                case PLATFORM_FROM:
                    return isSetPlatformFrom();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof setting_args)
                return this.equals((setting_args) that);
            return false;
        }

        public boolean equals(setting_args that) {
            if (that == null)
                return false;
            boolean this_present_platformFrom = true && this.isSetPlatformFrom();
            boolean that_present_platformFrom = true && that.isSetPlatformFrom();
            if (this_present_platformFrom || that_present_platformFrom) {
                if (!(this_present_platformFrom && that_present_platformFrom))
                    return false;
                if (!this.platformFrom.equals(that.platformFrom))
                    return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            List<Object> list = new ArrayList<Object>();
            boolean present_platformFrom = true && (isSetPlatformFrom());
            list.add(present_platformFrom);
            if (present_platformFrom)
                list.add(platformFrom);
            return list.hashCode();
        }

        @Override
        public int compareTo(setting_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }
            int lastComparison = 0;
            lastComparison = Boolean.valueOf(isSetPlatformFrom()).compareTo(other.isSetPlatformFrom());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetPlatformFrom()) {
                lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.platformFrom, other.platformFrom);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
            schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
            schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("setting_args(");
            boolean first = true;
            sb.append("platformFrom:");
            if (this.platformFrom == null) {
                sb.append("null");
            } else {
                sb.append(this.platformFrom);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws org.apache.thrift.TException {
            // check for sub-struct validity
            if (platformFrom != null) {
                platformFrom.validate();
            }
        }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
            } catch (org.apache.thrift.TException te) {
                throw new java.io.IOException(te);
            }
        }

        private static class setting_argsStandardSchemeFactory implements SchemeFactory {

            public setting_argsStandardScheme getScheme() {
                return new setting_argsStandardScheme();
            }
        }

        private static class setting_argsStandardScheme extends StandardScheme<setting_args> {

            public void read(org.apache.thrift.protocol.TProtocol iprot, setting_args struct) throws org.apache.thrift.TException {
                org.apache.thrift.protocol.TField schemeField;
                iprot.readStructBegin();
                while (true) {
                    schemeField = iprot.readFieldBegin();
                    if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                        break;
                    }
                    switch(schemeField.id) {
                        case // PLATFORM_FROM
                        1:
                            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                                struct.platformFrom = new TPlatformFrom();
                                struct.platformFrom.read(iprot);
                                struct.setPlatformFromIsSet(true);
                            } else {
                                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                            }
                            break;
                        default:
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    iprot.readFieldEnd();
                }
                iprot.readStructEnd();
                // check for required fields of primitive type, which can't be checked in the validate method
                struct.validate();
            }

            public void write(org.apache.thrift.protocol.TProtocol oprot, setting_args struct) throws org.apache.thrift.TException {
                struct.validate();
                oprot.writeStructBegin(STRUCT_DESC);
                if (struct.platformFrom != null) {
                    oprot.writeFieldBegin(PLATFORM_FROM_FIELD_DESC);
                    struct.platformFrom.write(oprot);
                    oprot.writeFieldEnd();
                }
                oprot.writeFieldStop();
                oprot.writeStructEnd();
            }
        }

        private static class setting_argsTupleSchemeFactory implements SchemeFactory {

            public setting_argsTupleScheme getScheme() {
                return new setting_argsTupleScheme();
            }
        }

        private static class setting_argsTupleScheme extends TupleScheme<setting_args> {

            @Override
            public void write(org.apache.thrift.protocol.TProtocol prot, setting_args struct) throws org.apache.thrift.TException {
                TTupleProtocol oprot = (TTupleProtocol) prot;
                BitSet optionals = new BitSet();
                if (struct.isSetPlatformFrom()) {
                    optionals.set(0);
                }
                oprot.writeBitSet(optionals, 1);
                if (struct.isSetPlatformFrom()) {
                    struct.platformFrom.write(oprot);
                }
            }

            @Override
            public void read(org.apache.thrift.protocol.TProtocol prot, setting_args struct) throws org.apache.thrift.TException {
                TTupleProtocol iprot = (TTupleProtocol) prot;
                BitSet incoming = iprot.readBitSet(1);
                if (incoming.get(0)) {
                    struct.platformFrom = new TPlatformFrom();
                    struct.platformFrom.read(iprot);
                    struct.setPlatformFromIsSet(true);
                }
            }
        }
    }
}
