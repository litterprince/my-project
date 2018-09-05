// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PersonMsg.proto

package com.test.proto;

public final class PersonMsg {
  private PersonMsg() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PersonOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Person)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * ID（必需）
     * </pre>
     *
     * <code>int32 id = 1;</code>
     */
    int getId();

    /**
     * <pre>
     * 姓名（必需）
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    java.lang.String getName();
    /**
     * <pre>
     * 姓名（必需）
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();

    /**
     * <pre>
     * email（可选）
     * </pre>
     *
     * <code>string email = 3;</code>
     */
    java.lang.String getEmail();
    /**
     * <pre>
     * email（可选）
     * </pre>
     *
     * <code>string email = 3;</code>
     */
    com.google.protobuf.ByteString
        getEmailBytes();

    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    java.util.List<java.lang.String>
        getFriendsList();
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    int getFriendsCount();
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    java.lang.String getFriends(int index);
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    com.google.protobuf.ByteString
        getFriendsBytes(int index);
  }
  /**
   * Protobuf type {@code Person}
   */
  public  static final class Person extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Person)
      PersonOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Person.newBuilder() to construct.
    private Person(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Person() {
      name_ = "";
      email_ = "";
      friends_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Person(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              id_ = input.readInt32();
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();

              name_ = s;
              break;
            }
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();

              email_ = s;
              break;
            }
            case 34: {
              java.lang.String s = input.readStringRequireUtf8();
              if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
                friends_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000008;
              }
              friends_.add(s);
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
          friends_ = friends_.getUnmodifiableView();
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.test.proto.PersonMsg.internal_static_Person_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.test.proto.PersonMsg.internal_static_Person_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.test.proto.PersonMsg.Person.class, com.test.proto.PersonMsg.Person.Builder.class);
    }

    private int bitField0_;
    public static final int ID_FIELD_NUMBER = 1;
    private int id_;
    /**
     * <pre>
     * ID（必需）
     * </pre>
     *
     * <code>int32 id = 1;</code>
     */
    public int getId() {
      return id_;
    }

    public static final int NAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object name_;
    /**
     * <pre>
     * 姓名（必需）
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * 姓名（必需）
     * </pre>
     *
     * <code>string name = 2;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int EMAIL_FIELD_NUMBER = 3;
    private volatile java.lang.Object email_;
    /**
     * <pre>
     * email（可选）
     * </pre>
     *
     * <code>string email = 3;</code>
     */
    public java.lang.String getEmail() {
      java.lang.Object ref = email_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        email_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * email（可选）
     * </pre>
     *
     * <code>string email = 3;</code>
     */
    public com.google.protobuf.ByteString
        getEmailBytes() {
      java.lang.Object ref = email_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        email_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FRIENDS_FIELD_NUMBER = 4;
    private com.google.protobuf.LazyStringList friends_;
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getFriendsList() {
      return friends_;
    }
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    public int getFriendsCount() {
      return friends_.size();
    }
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    public java.lang.String getFriends(int index) {
      return friends_.get(index);
    }
    /**
     * <pre>
     * 朋友（集合）
     * </pre>
     *
     * <code>repeated string friends = 4;</code>
     */
    public com.google.protobuf.ByteString
        getFriendsBytes(int index) {
      return friends_.getByteString(index);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (id_ != 0) {
        output.writeInt32(1, id_);
      }
      if (!getNameBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
      }
      if (!getEmailBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, email_);
      }
      for (int i = 0; i < friends_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, friends_.getRaw(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, id_);
      }
      if (!getNameBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
      }
      if (!getEmailBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, email_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < friends_.size(); i++) {
          dataSize += computeStringSizeNoTag(friends_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getFriendsList().size();
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.test.proto.PersonMsg.Person)) {
        return super.equals(obj);
      }
      com.test.proto.PersonMsg.Person other = (com.test.proto.PersonMsg.Person) obj;

      boolean result = true;
      result = result && (getId()
          == other.getId());
      result = result && getName()
          .equals(other.getName());
      result = result && getEmail()
          .equals(other.getEmail());
      result = result && getFriendsList()
          .equals(other.getFriendsList());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId();
      hash = (37 * hash) + NAME_FIELD_NUMBER;
      hash = (53 * hash) + getName().hashCode();
      hash = (37 * hash) + EMAIL_FIELD_NUMBER;
      hash = (53 * hash) + getEmail().hashCode();
      if (getFriendsCount() > 0) {
        hash = (37 * hash) + FRIENDS_FIELD_NUMBER;
        hash = (53 * hash) + getFriendsList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.test.proto.PersonMsg.Person parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.test.proto.PersonMsg.Person parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.test.proto.PersonMsg.Person parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.test.proto.PersonMsg.Person parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.test.proto.PersonMsg.Person prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Person}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Person)
        com.test.proto.PersonMsg.PersonOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.test.proto.PersonMsg.internal_static_Person_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.test.proto.PersonMsg.internal_static_Person_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.test.proto.PersonMsg.Person.class, com.test.proto.PersonMsg.Person.Builder.class);
      }

      // Construct using com.test.proto.PersonMsg.Person.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        id_ = 0;

        name_ = "";

        email_ = "";

        friends_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.test.proto.PersonMsg.internal_static_Person_descriptor;
      }

      @java.lang.Override
      public com.test.proto.PersonMsg.Person getDefaultInstanceForType() {
        return com.test.proto.PersonMsg.Person.getDefaultInstance();
      }

      @java.lang.Override
      public com.test.proto.PersonMsg.Person build() {
        com.test.proto.PersonMsg.Person result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.test.proto.PersonMsg.Person buildPartial() {
        com.test.proto.PersonMsg.Person result = new com.test.proto.PersonMsg.Person(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.id_ = id_;
        result.name_ = name_;
        result.email_ = email_;
        if (((bitField0_ & 0x00000008) == 0x00000008)) {
          friends_ = friends_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000008);
        }
        result.friends_ = friends_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return (Builder) super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.test.proto.PersonMsg.Person) {
          return mergeFrom((com.test.proto.PersonMsg.Person)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.test.proto.PersonMsg.Person other) {
        if (other == com.test.proto.PersonMsg.Person.getDefaultInstance()) return this;
        if (other.getId() != 0) {
          setId(other.getId());
        }
        if (!other.getName().isEmpty()) {
          name_ = other.name_;
          onChanged();
        }
        if (!other.getEmail().isEmpty()) {
          email_ = other.email_;
          onChanged();
        }
        if (!other.friends_.isEmpty()) {
          if (friends_.isEmpty()) {
            friends_ = other.friends_;
            bitField0_ = (bitField0_ & ~0x00000008);
          } else {
            ensureFriendsIsMutable();
            friends_.addAll(other.friends_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.test.proto.PersonMsg.Person parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.test.proto.PersonMsg.Person) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int id_ ;
      /**
       * <pre>
       * ID（必需）
       * </pre>
       *
       * <code>int32 id = 1;</code>
       */
      public int getId() {
        return id_;
      }
      /**
       * <pre>
       * ID（必需）
       * </pre>
       *
       * <code>int32 id = 1;</code>
       */
      public Builder setId(int value) {
        
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * ID（必需）
       * </pre>
       *
       * <code>int32 id = 1;</code>
       */
      public Builder clearId() {
        
        id_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object name_ = "";
      /**
       * <pre>
       * 姓名（必需）
       * </pre>
       *
       * <code>string name = 2;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * 姓名（必需）
       * </pre>
       *
       * <code>string name = 2;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * 姓名（必需）
       * </pre>
       *
       * <code>string name = 2;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 姓名（必需）
       * </pre>
       *
       * <code>string name = 2;</code>
       */
      public Builder clearName() {
        
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 姓名（必需）
       * </pre>
       *
       * <code>string name = 2;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        name_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object email_ = "";
      /**
       * <pre>
       * email（可选）
       * </pre>
       *
       * <code>string email = 3;</code>
       */
      public java.lang.String getEmail() {
        java.lang.Object ref = email_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          email_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * email（可选）
       * </pre>
       *
       * <code>string email = 3;</code>
       */
      public com.google.protobuf.ByteString
          getEmailBytes() {
        java.lang.Object ref = email_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          email_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * email（可选）
       * </pre>
       *
       * <code>string email = 3;</code>
       */
      public Builder setEmail(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        email_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * email（可选）
       * </pre>
       *
       * <code>string email = 3;</code>
       */
      public Builder clearEmail() {
        
        email_ = getDefaultInstance().getEmail();
        onChanged();
        return this;
      }
      /**
       * <pre>
       * email（可选）
       * </pre>
       *
       * <code>string email = 3;</code>
       */
      public Builder setEmailBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        email_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList friends_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureFriendsIsMutable() {
        if (!((bitField0_ & 0x00000008) == 0x00000008)) {
          friends_ = new com.google.protobuf.LazyStringArrayList(friends_);
          bitField0_ |= 0x00000008;
         }
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public com.google.protobuf.ProtocolStringList
          getFriendsList() {
        return friends_.getUnmodifiableView();
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public int getFriendsCount() {
        return friends_.size();
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public java.lang.String getFriends(int index) {
        return friends_.get(index);
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public com.google.protobuf.ByteString
          getFriendsBytes(int index) {
        return friends_.getByteString(index);
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public Builder setFriends(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureFriendsIsMutable();
        friends_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public Builder addFriends(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureFriendsIsMutable();
        friends_.add(value);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public Builder addAllFriends(
          java.lang.Iterable<java.lang.String> values) {
        ensureFriendsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, friends_);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public Builder clearFriends() {
        friends_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * 朋友（集合）
       * </pre>
       *
       * <code>repeated string friends = 4;</code>
       */
      public Builder addFriendsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        ensureFriendsIsMutable();
        friends_.add(value);
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Person)
    }

    // @@protoc_insertion_point(class_scope:Person)
    private static final com.test.proto.PersonMsg.Person DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.test.proto.PersonMsg.Person();
    }

    public static com.test.proto.PersonMsg.Person getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Person>
        PARSER = new com.google.protobuf.AbstractParser<Person>() {
      @java.lang.Override
      public Person parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Person(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Person> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Person> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.test.proto.PersonMsg.Person getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Person_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Person_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017PersonMsg.proto\"B\n\006Person\022\n\n\002id\030\001 \001(\005\022" +
      "\014\n\004name\030\002 \001(\t\022\r\n\005email\030\003 \001(\t\022\017\n\007friends\030" +
      "\004 \003(\tB\033\n\016com.test.protoB\tPersonMsgb\006prot" +
      "o3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Person_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Person_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Person_descriptor,
        new java.lang.String[] { "Id", "Name", "Email", "Friends", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
