
class NewBST<Key extends Comparable<Key>, Value> extends BST<Key, Value> {

        public Value predecessor(Key key, int m){
                int rank=rank(key);
                if (contains(key)==false) return null;
                else if(rank>=m){
                        int counter=0;
                        Key test=key;
                        Value testv=get(test);
                        Key next=floor(test);//same
                        while(counter<m){
                                test=next;//save old
                                testv=get(next);//save old
                                delete(test);
                                next=floor(test);//new
                                counter++;
                                put(test,testv);
                        }
                        return get(next);
                }else if(rank<m){
                        int targetnum=m-rank-1;//-1 is max itself
                        targetnum=targetnum%size();//this is ture//prevent it exceed again
                                int counter=0;
                                Key test=max();
                                Value testv=get(test);
                                Key next=floor(test);//same
                                while(counter<targetnum){
                                        test=next;//save old
                                        testv=get(next);//save old
                                        delete(test);
                                        next=floor(test);//new
                                        counter++;
                                        put(test,testv);
                                }
                        return get(next);
                }
                return null;
        }

        public Value successor(Key key, int m){
                int rank=size(key,max())-1;
                if (contains(key)==false) return null;
                else if(rank>=m){
                        int counter=0;
                        Key test=key;
                        Value testv=get(test);
                        Key next=ceiling(test);//same
                        while(counter<m){
                                test=next;//save old
                                testv=get(next);//save old
                                delete(test);
                                next=ceiling(test);//new
                                counter++;
                                put(test,testv);
                        }
                        return get(next);
                }else if(rank<m){
                        int targetnum=m-rank-1;//-1 is max itself
                        targetnum=targetnum%size();//this is ture//prevent it exceed again
                        int counter=0;
                        Key test=min();
                        Value testv=get(test);
                        Key next=ceiling(test);//same

                        while(counter<targetnum){
                                test=next;//save old
                                testv=get(next);//save old
                                delete(test);
                                next=ceiling(test);//new
                                counter++;
                                put(test,testv);
                        }

                        return get(next);

                }
                return null;
        }
}
