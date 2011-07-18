'''
@author: Wonjohn Choi
'''

import wx, popen2
#import L_Log

class L_Frame(wx.Frame):
    def __init__(self):
        '''
        constructor
        '''
        #set required variables
        self.setVariables()
        
        #add a frame
        wx.Frame.__init__(self, None, -1, self.title, size=(400,500))#size = wx.DisplaySize())
        
        #add a text screen
        self.inputField = wx.TextCtrl(self,self.ID_INPUTFIELD, style= wx.TE_MULTILINE|wx.TE_RICH)
        #self.logField = wx.TextCtrl(self,self.ID_LOGFIELD, 'Log',(0,350), (400,150), wx.TE_READONLY|wx.TE_MULTILINE)
        
        #add a status bar
        self.CreateStatusBar()
        self.SetStatusText("status bar")

        #create filemenu
        filemenu = wx.Menu()
        filemenu.Append(self.ID_ABOUT, "&About", "Information about this program")
        filemenu.AppendSeparator()
        filemenu.Append(self.ID_OPEN, "&Open\tCtrl-O", "Open a file")
        filemenu.Append(self.ID_SAVE, "&Save\tCtrl-S", "Save the current text") 
        filemenu.Append(self.ID_EXIT, "E&xit\tAlt-X", "Exit L. IDE")

        #create javamenu
        javamenu = wx.Menu()
        javamenu.Append(self.ID_COMPILE_JAVA, "&Compile\tCtrl-C-J", "Compile java source codes")
        javamenu.Append(self.ID_RUN_JAVA, "&Run\tCtrl-R-J", "Run java class file")
        #javamenu.Append(self.ID_RUN_JAVA, "&Run", "Run created java class file")
        
        #create pythonmenu
        pythonmenu = wx.Menu()
        pythonmenu.Append(self.ID_RUN_PYTHON, "&Run\tCtrl-R-P", "Run python source codes")
        
        #add menus
        menuBar = wx.MenuBar()
        menuBar.Append(filemenu, "&File")
        menuBar.Append(javamenu, "&Java")
        menuBar.Append(pythonmenu, "&Python")
        
        #set this menubar as standard
        self.SetMenuBar(menuBar)
        
        #filemenu actions
        wx.EVT_MENU(self, self.ID_ABOUT, self.onAbout)
        wx.EVT_MENU(self, self.ID_OPEN, self.onOpen)
        wx.EVT_MENU(self, self.ID_SAVE, self.onSave)
        wx.EVT_MENU(self, self.ID_EXIT, self.onExit)
       
        #javamenu actions
        wx.EVT_MENU(self, self.ID_COMPILE_JAVA, self.onCompileJava)
        wx.EVT_MENU(self, self.ID_RUN_JAVA, self.onRunJava)
        
        #pythonmenu actions
        wx.EVT_MENU(self, self.ID_RUN_PYTHON, self.onRunPython)
        
        #extra actions
        #wx.EVT_TEXT(self, self.ID_TEXT, self.onText)
        self.Bind(wx.EVT_TEXT, self.onText)
        #wx.EVT_CLOSE(self, self.onExit)
        #self.ShowFullScreen(True, style =wx.FULLSCREEN_ALL)
        
        self.Show(True)
        '''
        app = wx.App()
        self.logField = L_Log(self.ID_LOGFIELD)
        app.MainLoop()
        '''
    def onText(self, e):
        text = self.inputField.GetValue()
        if text.count('\n')<=29:
            
            reserved = None
            if self.dir != '.':
                if self.filename.lower().endswith('.java'):
                    reserved = self.javareserved
                elif self.filename.lower().endswith('.py'):
                    reserved = self.pythonreserved
                else:
                    return
            else:
                return
            
            for word, pos in self.coloringPos(text, 0):
                if reserved.count(word)>0:
                    self.inputField.SetStyle(pos, pos+len(word), wx.TextAttr("Purple", wx.NullColor))
                    self.inputField.SetStyle(pos+len(word), pos+len(word)+1, wx.TextAttr("Black", wx.NullColor))
        
        '''
        text = text.replace('\t', ' ').replace('\n', ' ')
        
        pos = 0
        for line in self.inputField.GetValue().split('\n'):
            for wordWithTap in line.split('\t'):
                for word in wordWithTap.split(' '):
                    if reserved.count(word)>0:
                        self.inputField.SetStyle(pos, pos+len(word), wx.TextAttr("Purple", wx.NullColor))
                        self.inputField.SetStyle(pos+len(word), pos+len(word)+1, wx.TextAttr("Black", wx.NullColor))
                    pos+=len(word)
                    pos+=1
                pos+=1
            pos+=1
            
        '''
            
    def coloringPos(self, text, pos):
        
        while len(text)!=0:
            
            if self.isEmpty(text[0]):
                text=text[1:]
                pos+=1
            else:
                word=''
                while len(text)!=0 and not self.isEmpty(text[0]):
                    word+=text[0]
                    text = text[1:]
                yield (word, pos)
                
                pos+=len(word)
            
    
    def isEmpty(self, ch):
        return ch==' ' or ch=='\t' or ch=='\n' or ch=='(' or ch==')' or ch=='{' or ch=='}' or ch=='\"' or ch=='\'' or ch==',' or ch=='.'
    
    def getJavaReserved(self, filename):
        self.javareserved = open(filename).read().split('\n')
        
    def getPythonReserved(self, filename):
        self.pythonreserved = open(filename).read().split('\n')
       
    def setVariables(self):
        #basic variables
        self.title = "L. IDE"
        self.dir = '.'
        self.filename = ''
        self.originalText = ''
        self.getJavaReserved('Java Reserved Words.lide')
        self.getPythonReserved('Python Reserved Words.lide')
        
        #text fields
        self.ID_INPUTFIELD = 5
        self.ID_LOGFIELD = 10
        
        #filemenu variables
        self.ID_ABOUT = 1000
        self.ID_EXIT = 1005
        self.ID_OPEN = 1010
        self.ID_SAVE = 1015
        
        
        #javamenu variables
        self.ID_COMPILE_JAVA = 2000
        self.ID_RUN_JAVA = 2005
        
        #print self.javareserved
        
        #pythonmenu variables
        self.ID_RUN_PYTHON = 3000
        
        
        #extra variables
        self.ID_TEXT = 10000
        
        
######################################################################################################
#########################################FILE MENU####################################################
######################################################################################################
    def onAbout(self, e):
        text = '\"'+self.title+'\" - the Light IDE Version 4 - developed by Wonjohn Choi'
        
        dialog = wx.MessageDialog(self, text, "About", wx.OK) 
        dialog.ShowModal()
        dialog.Destroy()

    def onExit(self, e): 
        option= self.inputField.GetValue() != self.originalText and self.promptSave('Would you like to save before closing?')
                
                
        if(option):
            self.onSave(-1)
            
        self.Close(True)
        
    def promptSave(self, message):
        
        dialog = wx.MessageDialog(self, message, 'Question Board', wx.YES_NO)
        
        option = dialog.ShowModal() == wx.ID_YES
        
        dialog.Destroy()
        
        return option
            

    def onSave(self, e):
        
        dialog=wx.FileDialog(self, "Save", self.dir, "", "*.*", wx.SAVE)
        
        if dialog.ShowModal() == wx.ID_OK:
            self.filename=dialog.GetFilename();
            self.dir=dialog.GetDirectory();
            self.originalText = self.inputField.GetValue()
            
            f=open(self.dir+"\\"+self.filename, "w")
            f.write(self.inputField.GetValue())
            f.close()
            
            self.sendMessage('Log', 'File saved in '+self.dir+"\\"+self.filename)

        dialog.Destroy()

    def onOpen(self, e):
        option= self.inputField.GetValue() != self.originalText and self.promptSave('Would you like to save before opening other file?')

        if(option):
            self.onSave(-1)
        
        dialog=wx.FileDialog(self, "Choose a file", self.dir, "", "*.*", wx.OPEN)
        
        if dialog.ShowModal() == wx.ID_OK:
            self.filename=dialog.GetFilename()
            self.dir=dialog.GetDirectory()
    
            f=open(self.dir+"\\"+self.filename, "r")
            self.inputField.SetValue(f.read())
            self.originalText = self.inputField.GetValue()
            f.close()
            
            self.sendMessage('Log', 'File opened from '+self.dir+"\\"+self.filename)
        dialog.Destroy()
        
        
######################################################################################################
#########################################JAVA MENU####################################################
######################################################################################################
    def onCompileJava(self, e):
        ready = False
        if self.dir!='.' and self.inputField.GetValue() == self.originalText:
            ready = True
        elif self.promptSave("Before compiling, you should save the current file: would you like to save?"):
            self.onSave(-1)
            ready = True
         
        if ready:
            if self.filename.endswith('.java'):
                popen2.popen2("javac "+self.dir+"\\"+self.filename)
                self.sendMessage('Log', self.filename+ ' compiled at '+self.dir )
            else:
                self.sendMessage('Log', 'File type should be .java (Java)')
                ready = False
        return ready
            
    def onRunJava(self, e):        
        if self.onCompileJava(-1):
            popen2.popen2(self.dir[0:2])
            popen2.popen2("cd "+self.dir)
            cmdout, cmdin = popen2.popen2("java "+self.filename[:-5])
                
            self.sendMessage('Log', 'File running from '+self.dir+'\\'+self.filename[:-5]+'.class')
            self.sendMessage('Output', cmdout.read().strip())
        else:
            self.sendMessage('Log', 'Fail to run due to failture in compiling')
    
   
            

######################################################################################################
#########################################PYTHON MENU##################################################
######################################################################################################
    def onRunPython(self, e):
        ready = False
        if self.dir!='.' and self.inputField.GetValue() == self.originalText:
            ready = True
        elif self.promptSave("Before running python source codes, you should save them: would you like to save?"):
            self.onSave(-1)
            ready = True
         
        if ready:
            if self.filename.endswith('.py'):
                cmdout, cmdin = popen2.popen2("python "+self.dir+"\\"+self.filename)
                
                self.sendMessage('Log', 'File running from '+self.dir+'\\'+self.filename)
                self.sendMessage('Output', cmdout.read().strip())
            else:
                self.sendMessage('Log', 'File type should be .py (Python)')
                ready = False
        return ready
    
    def sendMessage(self, type, message):
        if len(message.strip())!=0:
            print '------------------------'+type+'-----------------------------'
            print message
        
app = wx.App()
L_IDE = L_Frame()
app.MainLoop()