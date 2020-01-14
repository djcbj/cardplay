import * as React from 'react';
import "./bagItem.css"
function BagItem(props) {
    return (
      <div className="bagItem" >
        <div><img src={props.card.url} /></div>
        <span>{props.card.cardname}</span>
      </div>
    );
  }
  
  export default BagItem;