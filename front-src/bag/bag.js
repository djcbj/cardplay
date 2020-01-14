import * as React from 'react';
import BagItem from './bagItem'
function Bag(props) {
    const close=()=>props.close();
    const sell = (e)=>props.sell(e.target.id);
    var bagItems = null;
    if(props.bag){
      bagItems = props.bag.map((item)=>
      <div key={item.id}  className="item">
        <BagItem card={item} />
        <button id={item.id} onClick={sell}>出售</button>
      </div>
    );}
    if(props.show)
    return (
      <div className="Bag" >
        <button className="close button button-glow button-circle button-caution button-jumbo" onClick={close}>×</button>
        <div><h3>豆豆：{props.money}</h3></div>
        <br />
        {bagItems}
      </div>
    );
    else return null;
  }
  
  export default Bag;