'''
@author: Wonjohn Choi
'''
import wx

class L_Log(wx.Frame):
    '''
    classdocs
    '''


    def __init__(self, id):
        '''
        Constructor
        '''
        wx.Frame.__init__(self, None, -1, 'Log', size=(400,250))
        self.logField = wx.TextCtrl(self,id, style= wx.TE_MULTILINE | wx.TE_READONLY)
        self.Show(True)
        
    def println(self, log):
        self.logField.AppendText('------------------------Log-----------------------------\n')
        self.logField.AppendText(log+'\n')
        
if __name__ == '__main__':
    app = wx.App()
    log = L_Log(1000)
    app.MainLoop()