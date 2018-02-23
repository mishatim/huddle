using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SearcherForHuddle
{
    class LineGetter
    {
        public string GetLine(String file, int N)
        {
            string line = File.ReadLines(file).Skip(N).Take(1).First();
            return line;
        }
    }
}
