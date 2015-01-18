package modchu.pflmf;

public interface PFLMF_IPacketConstant {

	public final byte packet_FALSE = 0;
	public final byte packet_TRUE = 1;
	public final byte packet_IDNone = 0;
	public final byte packet_IDRemove = 1;
	public final byte packet_IDSitting = 2;
	public final byte packet_IDSleeping = 3;
	public final byte packet_IDAction = 4;
	public final byte packet_IDRunActionNumber = 5;
	public final byte packet_IDAll = 6;
	public final byte packet_IDLANRemove = 101;
	public final byte packet_IDLANSitting = 102;
	public final byte packet_IDLANSleeping = 103;
	public final byte packet_IDLANAction = 104;
	public final byte packet_IDLANRunActionNumber = 105;
	public final byte packet_IDLANAll = 106;
}
