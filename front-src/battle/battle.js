import * as React from 'react';
import axios from 'axios';
import "./battle.css"
import "../buttons.css"
function Battle(props) {

  const [final,setFinal] = React.useState(0);
  const [stage,setStage] = React.useState(0);
  const [current,setCurrent] = React.useState(0);
  const [cardList,setCardList] = React.useState(null);
  const [priList,setPriList] = React.useState(null);
  const [cards,setCards] = React.useState(null);
    
  React.useEffect(()=>{
    if(props.bag){
      setCards(props.bag.map((card)=>
        <div key={card.id} className="card">
          <img src={card.url} />
          <span>{card.cardname}</span>
          <button id={card.id} onClick={choose}>选择</button>
        </div>
      ))}
    if(props.list){
        setCardList(props.list.map((card)=>
          <div key={card.id}  className="card">
            <img src={card.url} />
            <br />
            <span>{card.cardname}</span>
          </div>
        ));
        setPriList(props.list.map((card)=>
        <div key={card.id} className="nocard">
        </div>
        ));
      }
    console.log(priList);
  },[props.list]);

  const close= ()=>{
    props.close();
    if(final>=2){
      axios.request({
        url:'adm/award',
        method:'get',
        params:{
          username:'admin',
          level:props.level
        }
      });
    }
    setStage(0);
    setFinal(0);
  }

  const choose= (e)=>{
    console.log(e.target.id);
    e.target.className = "bordered";
    setCurrent(e.target.id);
  }

  const next=()=>{
    let mylist = priList;
    mylist[stage] = cardList[stage];
    setPriList(mylist);
    if(props.list[stage].id < current)
      setFinal(final+1);
    console.log(JSON.stringify(priList));
    setStage(stage+1);
  };
  
 
 
  if(props.show)
    if(stage === 3)
      return (
        <div className="Battle result">
          <span>对手出牌是：</span>
          <br />
          {cardList}
          <br />
          <br />
          <span>战斗结果是：{final}胜</span>
          <br />
          <span>获得豆豆：{final>=2?props.level:0}</span>
          <button onClick={close}>确定</button>
        </div>
      );
    else
        return (
          <div className="Battle">
          {priList}
          <br />
          {cards}
          <br />
          <button className="login button button-glow button-rounded button-highlight button-primary" onClick={next}>确定</button>
        </div>
        );
  else return null;
}  
  export default Battle;