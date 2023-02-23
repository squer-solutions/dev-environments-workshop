# dev-environments-workshop
Workshop for experiencing and comparing various different approaches to creating local development environments.

## TILT
Tilt is a tool much like Docker Compose which is intended to help you work with Kubernetes as your primary driver for
local or remote development.

To get started, make sure that you have a local k8s cluster running. Most likely easiest with this tooling.
https://github.com/tilt-dev/ctlptl#docker-for-mac-enable-kubernetes-and-set-4-cpu

Then, install Tilt: https://docs.tilt.dev/install.html and run `tilt up` in the root of this repository.

You should be good to go.
