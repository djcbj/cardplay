import * as React from 'react';
import StoreItem from './storeItem'
import "../bag/bag.css"
function Store(props) {
    const close=()=>props.close();
    const buy = (e)=>props.buy(e.target.id);
    var storeItems = null;
    if(props.store){
      storeItems = props.store.map((item)=>
      <div key={item.id} className="item">
        <StoreItem card={item} />
        <button id={item.id} onClick={buy}>购买</button>
      </div>
    );
    }
    if(props.show)
      return (
        <div className="Bag">
          <button className="close button button-glow button-circle button-caution button-jumbo" onClick={close}>×</button>
          <div><h3>豆豆：{props.money}</h3></div>
          <br />
          {storeItems}
        </div>
      );
    else return null;
  }
  
  export default Store;