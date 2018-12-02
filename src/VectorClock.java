/**
 * Created by db on 29/05/16.
 */
public class VectorClock {

    public int VC[];

    public VectorClock(int size) {
        this.VC = new int[size];
        for(int i = 0; i < size; i++) {
           VC[i] = 0;
        }
    }

    public VectorClock(int vc[]) {
        this.VC = vc.clone();
    }

    public VectorClock(VectorClock vc) {
        this.VC = vc.getVC();
    }

    public VectorClock clone() {
        return new VectorClock(this);
    }

    public void incVectorClock(int position){
        VC[position]++;
    }

    public int[] getVC() {
        return VC.clone();
    }

    public int getValue(int ID) {
        return VC[ID];
    }

    public int getSize() { return VC.length; }

    public boolean equals(Object obj) {
        if(this == obj) return true;
        if((obj==null) || (this.getClass()!=obj.getClass())) return false;
        VectorClock VCObj = (VectorClock) obj;
        if(this.VC.length != VCObj.getVC().length) return false;
        for(int i = 0; i < VC.length; i++) {
            if(VC[i] != VCObj.getVC()[i]) {
                return false;
            }
        }
        return true;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Integer i: VC) {
            s.append(i);
        }
        return s.toString();
    }
}
