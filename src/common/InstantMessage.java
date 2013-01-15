package common;

public class InstantMessage {

		
		public int IMid;
		public int FromUserId;
		public int ToUserId;
		public String Message;
		public String sentdate;
		public int recievedstatus;
		;
		
		public InstantMessage()
		{
			
		}
		
		/**
		 * returns the blog's database numerical ID
		 * @return
		 */
		public int getIMid()
		{
			return IMid;
		}
		
		public void setIMid(int imid){
			
			this.IMid=imid;
		}
		
		public int getFromUserId()
		{
			return FromUserId;
		}
		
		
		public void setFromUserId(int fromid)
		{
			this.FromUserId=fromid;
		}
		
		public int getToUserId()
		{
			return ToUserId;
		}
		
		public void setToUserId(int toid)
		{
			this.ToUserId=toid;
		}
		
		
		public String getMessage()
		{
			return Message;
		}
		
		public void setMessage(String msg)
		{
			this.Message = msg;
		}
		
		
		public String getSentDate()
		{
			return sentdate;
		}
		
		public void setSentDate(String sntdte)
		{
			this.sentdate = sntdte;
		}
		
		public int getRecieved()
		{
			return recievedstatus;
		}
		public void setRecieved(int recst)
		{
			this.recievedstatus=recst;
		}

}
