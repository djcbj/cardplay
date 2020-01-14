import * as React from 'react';
import "./battleIndex.css"
function BattleIndex(props) {
  const close = ()=>props.close();
  const start = (e)=>{
    props.close();
    props.start(e.target.id);
  };
  if(props.show)
    return (
      <div className="battleIndex">
        <button className="close button button-glow button-circle button-caution button-jumbo" onClick={close}>×</button>
        <br />
        <button className="button button-glow button-border button-rounded button-primary" id="1" onClick={start}>难度1</button>
        <br />
        <button className="button button-glow button-border button-rounded button-primary" id="2" onClick={start}>难度2</button>
        <br />
        <button className="button button-glow button-border button-rounded button-primary" id="3" onClick={start}>难度3</button>
        <br />
        <button className="button button-glow button-border button-rounded button-primary" id="4" onClick={start}>难度4</button>
        <br />
        <button className="button button-glow button-border button-rounded button-primary" id="5" onClick={start}>难度5</button>
      </div>
    );
  else return null;
}
  
  export default BattleIndex;