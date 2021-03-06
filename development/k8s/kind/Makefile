.EXPORT_ALL_VARIABLES:
.PHONY: docker-all

VERSION := $(shell cat ../../../version.txt)
default: kind-serve

# https://kind.sigs.k8s.io/docs/user/quick-start/
# > curl -Lo ./kind https://github.com/kubernetes-sigs/kind/releases/download/v0.5.1/kind-linux-amd64
# > chmod +x ./kind
# > sudo mv ./kind /usr/local/bin/kind

# WARNING Kind v7.0.0 prevents the kraken-runtime-kubernetes to make any request (SSL related errors)
kind-install:
	curl -Lo ./kind https://github.com/kubernetes-sigs/kind/releases/download/v0.8.1/kind-linux-amd64
	chmod +x ./kind
	sudo mv ./kind /usr/local/bin/kind

kind-serve: kind-clean kind-start kind-ingress kind-docker-copy-all

kind-start:
	kind create cluster --config kind-config.yaml
	kubectl label nodes kind-worker com.octoperf/node-name=kraken-1

# https://kind.sigs.k8s.io/docs/user/ingress/
kind-ingress:
	kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/9aa1a59e1d3e3e948ef7f91311013008a0be7c7f/deploy/static/provider/kind/deploy.yaml
# 	kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/provider/kind/deploy.yaml
	kubectl wait --namespace ingress-nginx --for=condition=ready pod --selector=app.kubernetes.io/component=controller --timeout=300s

kind-docker-copy-all:
	kind load docker-image octoperf/kraken-keycloak:$(VERSION)
	kind load docker-image octoperf/kraken-ee-kubernetes-all:$(VERSION)
	kind load docker-image octoperf/kraken-gatling-runner:$(VERSION)
	kind load docker-image octoperf/kraken-gatling-log-parser:$(VERSION)
	kind load docker-image octoperf/kraken-telegraf:$(VERSION)
	kind load docker-image octoperf/kraken-gatling-recorder:$(VERSION)
	kind load docker-image octoperf/kraken-har-parser:$(VERSION)
	kind load docker-image octoperf/kraken-documentation:$(VERSION)
	kind load docker-image octoperf/kraken-gatling-ui:$(VERSION)
	kind load docker-image octoperf/kraken-administration-ui:$(VERSION)
	-kind load docker-image octoperf/kraken-echo:$(VERSION)

kind-clean:
	-kind delete cluster