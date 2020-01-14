import * as React from 'react';
import "../bag/bagItem.css"
function StoreItem(props) {
    return (
      <div className="bagItem">
        <div><img src={props.card.url} /></div>
        <span>{props.card.cardname}</span>
      </div>
    );
  }
  
  export default StoreItem;