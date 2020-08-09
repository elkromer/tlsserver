source common.tcl

file mkdir $userdir\\$username
set userfile [open $userdir\\$username\\$username.log a+]

puts $userfile "TLSServer $vermaj"
puts $userfile "Created at $timestamp"