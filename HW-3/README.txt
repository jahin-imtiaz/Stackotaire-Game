//if User needs help, perform this code
//This command search through all stacks and and moves a card if
//possible. this gives priority to the foundation piles, then waste 
//stacks and then tableaus. which means it will always look to bring
//a card to the foundation piles first.if no move is possible, it 
//draws a card for the user if available.
else if(input.equalsIgnoreCase("HELP")){
	//increase the helpCounter
	helpCount++;
	//if any cards were possible to move to foundation print this
	if(helpFoundation()){
		System.out.print(" ");
	}
	//otherwise if any cards were possible to move to tableau
	//from waste print this
	else if(helpWasteToTableau()){
		System.out.print(" ");
	}
	//otherwise if any cards were possible to move from another
	//tableau, print this
	else if(helpTableauToTableau()){
		System.out.print(" ");
	}
	//otherwise draw a card 
	else{
		//check if the stock is empty or not
		//if empty, move all the cards from the waste stack
		//to the stock stack and then draw the card
		if(stockStack.isEmpty()){
			while(!wasteStack.isEmpty()){
				stockStack.push(wasteStack.pop());
			}
		}
		//move the top card faced up to the waste stack 
		stockStack.peek().setFaceUp(true);
		wasteStack.push(stockStack.pop());
		
		//display the game board
		System.out.println(" ");
		displayGameBoard();
		System.out.println(" ");
		System.out.println("A Card Has been drawn from the stock. ");
	}
}


/**
	 * Checks if all the cards in the deck is faced up or not. if all cards are
	 * faced up, its a winning board
	 * @return
	 * 		returns true, if all cards are faced up
	 */
	public static boolean checkWinningBoard(){
		int i=0;
		while(i<52){
			if(deckList.get(i).isFaceUp == false)
				return false;
			i++;
		}
		return true;
	}
	/**
	* this method search through all the tableau piles and waste stack to find
	* a suitable card to move to the foundation array.
	* * @return
	 * 		returns true if any card was possible to move
	*/
	public static boolean helpFoundation(){
		//go through all FoundationStack
		//check if any card can be brought or not from tableau or waste
		for(int i=1; i<5; i++){
			//first priority is my waste stack
			//if waste stack is not empty, check any card can be
			//brought or not. otherwise look into tableau piles
			if(!wasteStack.isEmpty()){
				//if foundation stack is empty, only an ACE card can 
				//be brought from the stack
				if(foundationArray[i].isEmpty() && wasteStack.peek().getValue() == 1){
					foundationArray[i].push(wasteStack.pop());
		
					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1"+" to F"+i+" .");
					System.out.println(" ");
					return true;
				}
				//if foundation card is not empty, any card that is same
				//suit and one value higher than the top, can be braught
				else if(!foundationArray[i].isEmpty() && wasteStack.peek()
				  .getValue() == foundationArray[i].peek().getValue()+1 && 
				  wasteStack.peek().getSuit() == foundationArray[i].peek()
				  .getSuit() ){
					
					foundationArray[i].push(wasteStack.pop());
					
					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1"+" to F"+i+" .");
					System.out.println(" ");
					return true;
				}
				//if no cards could be brought from waste stacks,
				//search into tableau piles
				else{
					//go through all tableau stacks
					for(int j=1; j<8;j++){
						//if foundation array is empty, only an ace can
						//be brought from the tableaus
						if(foundationArray[i].isEmpty()){
							//search only when the tableau isn't empty
							if(!tableauArray[j].isEmpty()){
								//look for Ace cards
								if(tableauArray[j].peek().getValue()== 1){
									foundationArray[i].push(tableauArray[j].pop());
									
									displayGameBoard();
									System.out.println(" ");
									System.out.println("Moved from T"+j+" to"
											+ " F"+i+" .");
									System.out.println(" ");
									return true;
								}
							}
						}
						//if foundation array is not empty and if tableau array
						//is not empty, bring any card that is one higher and same suit
						else if(!tableauArray[j].isEmpty()){
							
							if(tableauArray[j].peek().getValue() == foundationArray
							  [i].peek().getValue()+1 && tableauArray[j].peek()
							  .getSuit() == foundationArray[i].peek().getSuit() ){
								
								foundationArray[i].push(tableauArray[j].pop());
								
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to F"+i+" .");
								System.out.println(" ");
								return true;
							}
						}
					}
				}
			}
			//only if waste stack was empty, loop into tableau piles first.
			else{
				//go through all tableau stacks
				for(int j=1; j<8;j++){
					//if foundation array is empty, only an ace can
					//be brought
					if(foundationArray[i].isEmpty()){
						if(!tableauArray[j].isEmpty()){
							if(tableauArray[j].peek().getValue()== 1){
								foundationArray[i].push(tableauArray[j].pop());
								
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to F"+i+" .");
								System.out.println(" ");
								return true;
							}
						}
					}
					//if foundation and tableau array are both not empty
					//bring any card that is one higher and same suit
					else if(!tableauArray[j].isEmpty()){
					
						if(tableauArray[j].peek().getValue() == foundationArray[i]
						  .peek().getValue()+1 && tableauArray[j].peek().getSuit()
						  == foundationArray[i].peek().getSuit() ){
							
							foundationArray[i].push(tableauArray[j].pop());
							
							displayGameBoard();
							System.out.println(" ");
							System.out.println("Moved from T"+j+" to F"+i+" .");
							System.out.println(" ");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * this method tries to bring any valid card from waste pile to tableau 
	 * piles.
	 * @return
	 * 		returns true if any card was possible to move
	 */
	public static boolean helpWasteToTableau(){
		//goes through all tableau piles to see if any pile available to receive
		//any card from the stack
		for(int i=1;i<8;i++){
			//if the current tableau pile is empty, only bring King
			if(tableauArray[i].isEmpty() && !wasteStack.isEmpty() ){
				if(wasteStack.peek().getValue()== 13){
					tableauArray[i].push(wasteStack.pop());

					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1 to T"+i+" .");
					System.out.println(" ");
					return true;
				}
			}
			//if its not empty, bring any valid card
			else if(!tableauArray[i].isEmpty() && !wasteStack.isEmpty()){
				if(tableauArray[i].peek().getValue() == wasteStack.peek()
				  .getValue()+1 && tableauArray[i].peek().isRed() != wasteStack
				  .peek().isRed()){
					
					tableauArray[i].push(wasteStack.pop());

					displayGameBoard();
					System.out.println(" ");
					System.out.println("Moved from W1 to T"+i+" .");
					System.out.println(" ");
					return true;
				}
			}
		}
		return false;	
	}
	/**
	 * this method tries to bring any valid card from other tableau piles to 
	 * the current tableau. this method first looks for a top card and after
	 * being unsuccessful, it looks if N cards can be brought or not
	 * @return
	 * 		returns true if any card was possible to move
	 */
	public static boolean helpTableauToTableau(){
		//go through all tableaus
		for(int i=1; i<8; i++){
			//if current tableau is empty, look for a King in other tableau's
			if (tableauArray[i].isEmpty()){
				for(int j=1; j<8;j++ ){
					//check only when the tableau is not empty
					if(!tableauArray[j].isEmpty()){
						//check if any King can be brought from the top of a pile
						if(tableauArray[j].peek().getValue() == 13){
							tableauArray[i].push(tableauArray[j].pop());
							
							//if theres is no card left after removing the king
							//this means, this king was already in a valid place
							// so the move is not good. so put back the king
							if(tableauArray[j].isEmpty()){
								tableauArray[j].push(tableauArray[i].pop());
							}
							//otherwise print the move
							else{
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
						}
						//if no king was available on the top, check if any 
						//faced up king is available inside other cards of 
						//that tableau  or not
						else{
							//create a new stack and put all the facedup cards
							CardStack tempStack1 = new CardStack();
							//tempstack2 only gets the faced down cards
							CardStack tempStack2 = new CardStack();
							while(!tableauArray[j].isEmpty()){
								if(tableauArray[j].peek().isFaceUp){
									tempStack1.push(tableauArray[j].pop());
								}
								else tempStack2.push(tableauArray[j].pop());
							}
							//keep a copy of the top card of tempStack1
							Card tempCard;
							tempCard = tempStack1.peek();
							//get the size of tempStack 2 to see if it is empty
							int tempSize= tempStack2.size();
							//put all the faced down cards back
							while(!tempStack2.isEmpty()){
								tableauArray[j].push(tempStack2.pop());
							}
							//put all the faced up cards back
							while(!tempStack1.isEmpty()){
								tableauArray[j].push(tempStack1.pop());
							}
							//check if the top card of the new stack is king
							//or not.if king, move all the cards if tempSize!=0
							if(tempCard.getValue() == 13){
								//tempSize = 0 indicates that the King was in the
								//first position of a pile. which was a valid place
								if(tempSize!=0){
									//put the faced up cards in tempStack1
									//and faced down cards in tempStack2
									while(!tableauArray[j].isEmpty()){
										if(tableauArray[j].peek().isFaceUp){
											tempStack1.push(tableauArray[j].pop());
										}
										else tempStack2.push(tableauArray[j].pop());
									}
									//put back the faced down cards
									while(!tempStack2.isEmpty()){
										tableauArray[j].push(tempStack2.pop());
									}
									//put all the faced up cards in the new pile
									while(!tempStack1.isEmpty()){
										tableauArray[i].push(tempStack1.pop());
										
									}
									displayGameBoard();
									System.out.println(" ");
									System.out.println("Moved from T"+j+" to T"+i+" .");
									System.out.println(" ");
									return true;
								}	
							}
							//if the top card of tempStack1, which carries the
							//faced up cards wasn't a King, put all the cards back
							else{
								while(!tempStack1.isEmpty()){
									tableauArray[j].push(tempStack1.pop());
								}
							}
						}
					}
				}
			}
			//if current tableau is not empty, look for any valid card that 
			//can be brought from other tableau piles
			else if(!tableauArray[i].isEmpty()){
				for(int j=1; j<8;j++ ){
					//look for other places only if that place is not empty
					if(!tableauArray[j].isEmpty()){
						//check for validity
						if(tableauArray[j].peek().getValue() == tableauArray[i].
						  peek().getValue()-1 && tableauArray[i].peek().isRed()
						  != tableauArray[j].peek().isRed()){		
							
							tableauArray[i].push(tableauArray[j].pop());
							
							//now check the table that i just removed the card
							//from. if the top card is faced up, this means 
							//the card i removed was in a good place, and the 
							//move is not necessary.
							//if the tableau becomes empty, this means its a
							//valid  move
							if(tableauArray[j].isEmpty()){
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
							//if the tableau doesn't become empty and the top card
							//is faced down, then its also a valid move
							else if(!tableauArray[j].isEmpty() && !tableauArray[j]
							  .peek().isFaceUp()){
								
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
							//otherwise it was invalid move
							else{
								tableauArray[j].push(tableauArray[i].pop());
							}
						}
						// check if any N number of faced up cards can be
						//braught from other tableau's or not
						else{
							//create a new stack and put all the facedup cards
							CardStack tempStack1 = new CardStack();
							CardStack tempStack2 = new CardStack();
							while(!tableauArray[j].isEmpty()){
								if(tableauArray[j].peek().isFaceUp){
									tempStack1.push(tableauArray[j].pop());
								}
								else tempStack2.push(tableauArray[j].pop());
							}
							//keep a copy of the top card of tempStack
							Card tempCard;
							tempCard = tempStack1.peek();

							//put all the faced down cards back
							while(!tempStack2.isEmpty()){
								tableauArray[j].push(tempStack2.pop());
							}
							while(!tempStack1.isEmpty()){
								tableauArray[j].push(tempStack1.pop());
							}
							//check if the top card of the new stack is king
							//or not.if king, move all the cards
							if(tempCard.getValue() == tableauArray[i].peek().
							  getValue()-1 && tempCard.isRed()!=tableauArray[i]
							  .peek().isRed()){ 
								
								while(!tableauArray[j].isEmpty()){
									if(tableauArray[j].peek().isFaceUp){
										tempStack1.push(tableauArray[j].pop());
									}
									else tempStack2.push(tableauArray[j].pop());
								}
								
								while(!tempStack2.isEmpty()){
									tableauArray[j].push(tempStack2.pop());
								}
								
								while(!tempStack1.isEmpty()){
									tableauArray[i].push(tempStack1.pop());
									
								}
								displayGameBoard();
								System.out.println(" ");
								System.out.println("Moved from T"+j+" to T"+i+" .");
								System.out.println(" ");
								return true;
							}
							
						}
						
					}
				}
			}
			//if current tableau is empty look for other tableaus where K is n cards down
			//also if current tableau is not empty look for n cards that can be moved.
		}
		return false;
	}
	






