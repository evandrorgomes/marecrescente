import { BaseEntidade } from '../base.entidade';

export class EntidadeUtil{
    public static compareEquals(obj1:BaseEntidade, obj2:BaseEntidade): Boolean{
        return (obj1 != null && obj2 != null && JSON.stringify(obj1) === JSON.stringify(obj2));
    }

    public static isEmpty(obj): Boolean {
        for(var i in obj){ if(obj.hasOwnProperty(i)){return false;}}
       return true;
     };
}