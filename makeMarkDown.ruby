def createWord(number)
$x="";
(0..number).each do |i|
$x=$x+ (rand(94)+32).chr
end
return $x
end
#########################
def createPart()

$kind=1+rand(10)
$str=""
case $kind
when 1
	$str='*斜体'+createWord(1+rand(15))+'*'
when 2
	$str='**粗体'+createWord(1+rand(15))+'**'
when 3
	$str="一级标题"+createWord(1+rand(50))+"\n====================="
when 4
	$str="二级标题"+createWord(1+rand(50))+"\n---------------------"
when 5
	$str='['+"链接"+createWord(1+rand(10))+']'+"(www.google.com)"
when 6
	$str="* 无序列表"+createWord(1+rand(10))
when 7
	$str=""+(1+rand(9)).to_s+". 有序列表"+createWord(1+rand(10))
when 8
	$str="> 引用"+createWord(1+rand(20))
when 9
	$str="    代码块"+createWord(1+rand(80))
else
	$str=""+'!['+"图片"++createWord(1+rand(10))+']'+"(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9mdJP5XNxmLakU53GSwigAZFxfEoYAevrb6lLP1rqtc6VSu9_Bw)"
end

	$str=$str+"\n\n"

return $str
end
#########################
def createMD()
$str=""
$part=20+rand(20)
(0..$part).each do |i|
$str=$str+createPart()
end
return $str

end
##########################
for i in 1..5

$filename="file"+i.to_s+".md"

file=File.new($filename,"w")
$str=""

$str=createMD()

file.syswrite($str)
file.close

end


